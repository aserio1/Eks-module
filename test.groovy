
#!/usr/bin/env python3

# Jenkins Workspace / EFS Cleanup - DRY RUN
#
# Checks:
#   1. Stale .terraform directories
#   2. Old numeric build workspaces
#   3. Orphaned Jenkins jobs and branches
#
# No hardcoded project folders.
# No directory size calculations.
# No deletion.
#
# Jenkins credentials are supplied by the existing pipeline.
#
# Example:
#   python3 cleanup_jenkins_efs.py --root /jenkins_jobs
#
# Or:
#   python3 cleanup_jenkins_efs.py --root /path/visible/to/jenkins

import os
import sys
import time
import json
import argparse
import urllib.parse
import urllib.request
import urllib.error
import base64
import re

from pathlib import Path

STALE_DAYS = 90
MAX_WORKERS = 8
DRY_RUN = True
CALCULATE_SIZES = False
VALIDATE_JENKINS_JOBS = True

MAX_SCAN_SECONDS = 300
PROGRESS_INTERVAL = 5000

# These are internal/cache folders, not Jenkins project names.
SKIP_DIRS = {
    ".git",
    ".svn",
    ".hg",
    "node_modules",
    "__pycache__",
    ".venv",
    "venv",
    ".cache",
    ".mypy_cache",
    ".pytest_cache",
    ".tox",
    ".gradle",
    ".m2",
    ".idea",
    ".vscode"
}


def normalize_job_name(name):
    """Normalize Jenkins job names for comparison."""
    return urllib.parse.unquote(name).strip("/")


def get_workspace_root():
    """
    Derive the workspace root from the current Jenkins job
    when --root is not supplied.

    Expected ws() pattern:
      workspace/${JOB_NAME.replace('%2F','/')}/${BUILD_NUMBER}
    """

    workspace = os.environ.get("WORKSPACE", "")
    job_name = os.environ.get("JOB_NAME", "")
    build_number = os.environ.get("BUILD_NUMBER", "")

    if not (workspace and job_name and build_number):
        return None

    normalized_job = job_name.replace("%2F", "/")
    suffix = Path(normalized_job) / build_number

    workspace_path = Path(workspace).absolute()
    parts = workspace_path.parts
    suffix_parts = suffix.parts

    for i in range(len(parts) - len(suffix_parts) + 1):
        if tuple(parts[i:i + len(suffix_parts)]) == suffix_parts:
            return str(Path(*parts[:i]))

    return None


def get_jenkins_config():
    """
    Reuse Jenkins credentials already exported
    by the existing Jenkins pipeline.

    Expected environment variables:
      JENKINS_URL
      JENKINS_USER
      JENKINS_API_TOKEN

    If credentials are not provided, Jenkins API
    validation is unavailable.
    """

    url = os.environ.get("JENKINS_URL", "").rstrip("/")
    user = os.environ.get("JENKINS_USER", "")
    token = os.environ.get("JENKINS_API_TOKEN", "")

    if not url:
        return None

    return {
        "url": url,
        "user": user,
        "token": token
    }


def jenkins_get_json(config, path):
    """Retrieve JSON from Jenkins API."""

    url = config["url"] + path

    request = urllib.request.Request(
        url,
        headers={"Accept": "application/json"}
    )

    if config["user"] and config["token"]:
        credentials = (
            config["user"] + ":" + config["token"]
        ).encode("utf-8")

        auth = base64.b64encode(credentials).decode("ascii")

        request.add_header(
            "Authorization",
            "Basic " + auth
        )

    with urllib.request.urlopen(
        request,
        timeout=20
    ) as response:
        return json.load(response)


def get_jenkins_jobs(config):
    """
    Fetch Jenkins jobs recursively through the tree API.

    Returns job full names and build metadata.
    """

    tree = (
        "jobs[fullName,_class,"
        "jobs[fullName,_class,"
        "jobs[fullName,_class]]]"
    )

    path = "/api/json?tree=" + urllib.parse.quote(
        tree,
        safe="[],"
    )

    data = jenkins_get_json(config, path)

    jobs = set()
    folders = set()

    def walk(items):
        for item in items:

            name = item.get("fullName")

            if not name:
                continue

            normalized = normalize_job_name(name)

            if item.get("jobs") is not None:
                folders.add(normalized)
                walk(item.get("jobs", []))
            else:
                jobs.add(normalized)

    walk(data.get("jobs", []))

    return jobs, folders


def get_active_builds(config):
    """
    Retrieve active executors from Jenkins.

    This is a controller-level check and may not
    cover every cloud/agent execution configuration.
    """

    path = (
        "/computer/api/json?"
        "tree=computer[executors[currentExecutable[url]],"
        "oneOffExecutors[currentExecutable[url]]]"
    )

    data = jenkins_get_json(config, path)

    active = set()

    for computer in data.get("computer", []):
        for key in ("executors", "oneOffExecutors"):
            for executor in computer.get(key, []):
                executable = executor.get("currentExecutable")

                if executable:
                    url = executable.get("url")

                    if url:
                        active.add(url.rstrip("/"))

    return active


def scan_directories(root, cutoff, max_seconds):
    """
    Scan directories without calculating sizes.

    Discover .terraform directories and numeric folders.

    Continue into numeric folders to discover Terraform
    caches within build workspaces.
    """

    start = time.monotonic()
    stack = [Path(root)]

    terraform_dirs = []
    numeric_dirs = []

    scanned = 0
    errors = 0
    skipped = 0
    timed_out = False

    while stack:

        if time.monotonic() - start >= max_seconds:
            timed_out = True
            break

        current = stack.pop()
        scanned += 1

        if scanned % PROGRESS_INTERVAL == 0:
            print(
                f"Progress: scanned {scanned} directories",
                flush=True
            )

        try:
            with os.scandir(current) as entries:
                for entry in entries:

                    try:
                        if not entry.is_dir(
                            follow_symlinks=False
                        ):
                            continue

                        if entry.name in SKIP_DIRS:
                            skipped += 1
                            continue

                        if entry.name == ".terraform":
                            info = entry.stat(
                                follow_symlinks=False
                            )

                            terraform_dirs.append({
                                "path": entry.path,
                                "mtime": info.st_mtime
                            })

                            continue

                        if entry.name.isdigit():
                            info = entry.stat(
                                follow_symlinks=False
                            )

                            numeric_dirs.append({
                                "path": entry.path,
                                "mtime": info.st_mtime
                            })

                        stack.append(Path(entry.path))

                    except OSError:
                        errors += 1

        except OSError:
            errors += 1

    return {
        "terraform": terraform_dirs,
        "numeric": numeric_dirs,
        "scanned": scanned,
        "skipped": skipped,
        "errors": errors,
        "timed_out": timed_out,
        "elapsed": time.monotonic() - start
    }


def relative_parts(root, path):
    return Path(path).relative_to(root).parts


def identify_orphans(root, numeric_dirs, jobs, folders):
    """
    Compare numeric build directories to Jenkins job names.

    A numeric folder is interpreted as a possible build
    directory, and its parent as a possible Jenkins job.

    An unknown name is only an orphan candidate,
    not a confirmed safe deletion.
    """

    orphan_candidates = set()
    verified_jobs = set()
    unverified = set()

    for item in numeric_dirs:

        path = Path(item["path"])

        try:
            parts = relative_parts(root, path)
        except ValueError:
            continue

        if len(parts) < 2:
            unverified.add(str(path))
            continue

        candidate_job = normalize_job_name(
            "/".join(parts[:-1])
        )

        if candidate_job in jobs:
            verified_jobs.add(candidate_job)

        elif candidate_job in folders:
            unverified.add(str(path))

        else:
            orphan_candidates.add(candidate_job)

    return orphan_candidates, verified_jobs, unverified


def main():

    parser = argparse.ArgumentParser(
        description="Jenkins EFS cleanup dry-run"
    )

    parser.add_argument(
        "--root",
        default=None,
        help="Root directory to scan"
    )

    parser.add_argument(
        "--stale-days",
        type=int,
        default=STALE_DAYS
    )

    parser.add_argument(
        "--max-seconds",
        type=int,
        default=MAX_SCAN_SECONDS
    )

    parser.add_argument(
        "--execute",
        action="store_true"
    )

    args = parser.parse_args()

    if args.execute:
        print("ERROR: Deletion is disabled.")
        sys.exit(1)

    if args.stale_days < 0 or args.max_seconds <= 0:
        parser.error("Invalid retention or timeout")

    root = args.root or get_workspace_root()

    if not root:
        print(
            "ERROR: Could not determine workspace root. "
            "Use --root to specify a visible directory."
        )
        sys.exit(1)

    root = Path(root).resolve()

    if not root.is_dir():
        print(f"ERROR: Directory not found: {root}")
        sys.exit(1)

    cutoff = time.time() - args.stale_days * 86400

    print("========================================")
    print("JENKINS CLEANUP - DRY RUN")
    print("========================================")
    print(f"Root directory : {root}")
    print(f"Retention      : {args.stale_days} days")
    print(f"Max scan time  : {args.max_seconds} seconds")
    print("Mode           : DRY RUN")
    print()

    config = get_jenkins_config()

    jobs = set()
    folders = set()
    active_builds = set()
    validation_ok = False

    if VALIDATE_JENKINS_JOBS and config:

        try:
            jobs, folders = get_jenkins_jobs(config)
            active_builds = get_active_builds(config)

            validation_ok = True

            print(
                f"Jenkins jobs loaded: {len(jobs)}"
            )

            print(
                f"Active executables found: "
                f"{len(active_builds)}"
            )

        except Exception as error:
            print(
                f"WARNING: Jenkins validation unavailable: "
                f"{error}"
            )

    else:
        print(
            "WARNING: Jenkins API validation unavailable."
        )

    results = scan_directories(
        root,
        cutoff,
        args.max_seconds
    )

    stale_terraform = [
        item
        for item in results["terraform"]
        if item["mtime"] < cutoff
    ]

    stale_numeric = [
        item
        for item in results["numeric"]
        if item["mtime"] < cutoff
    ]

    orphan_candidates = set()
    unverified = set()

    if validation_ok:
        orphan_candidates, _, unverified = identify_orphans(
            root,
            stale_numeric,
            jobs,
            folders
        )

    else:
        unverified = {
            item["path"]
            for item in stale_numeric
        }

    print()
    print("========================================")
    print("JENKINS CLEANUP - DRY RUN SUMMARY")
    print("========================================")
    print(
        f"Terraform directories found     : "
        f"{len(results['terraform'])}"
    )
    print(
        f"Stale Terraform directories     : "
        f"{len(stale_terraform)}"
    )
    print(
        f"Old numeric workspaces          : "
        f"{len(stale_numeric)}"
    )
    print(
        f"Orphaned job candidates         : "
        f"{len(orphan_candidates)}"
    )
    print(
        f"Unverified candidates           : "
        f"{len(unverified)}"
    )
    print(
        f"Directories scanned             : "
        f"{results['scanned']}"
    )
    print(
        f"Directories skipped             : "
        f"{results['skipped']}"
    )
    print(
        f"Scan errors                     : "
        f"{results['errors']}"
    )
    print(
        f"Execution time                  : "
        f"{results['elapsed']:.2f} seconds"
    )
    print(
        f"Scan status                     : "
        f"{'PARTIAL' if results['timed_out'] or results['errors'] else 'COMPLETE WITH EXCLUSIONS'}"
    )
    print("Directories deleted             : 0")
    print("Files deleted                   : 0")
    print("========================================")
    print("DRY-RUN COMPLETE - NOTHING DELETED")
    print("========================================")


if __name__ == "__main__":
    main()
