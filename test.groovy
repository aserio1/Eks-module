#!/usr/bin/env python3
"""
cloud9_audit.py
Scans Cloud9 environments in us-east-1 across every AWS profile
found in ~/.aws/config, enriched with last-access metadata.

Last-access strategy (in order):
  1. describe_environment_memberships  → lastAccess (epoch) per member
  2. CloudTrail LookupEvents           → most recent event referencing the
                                         environment ID (90-day window)
  3. Falls back to "never/unknown"

Outputs:
  - Summary table of all environments
  - Jenkins parameter block for stale environments (STALE_DAYS threshold)
"""

import boto3
import configparser
import json
import os
from datetime import datetime, timezone, timedelta
from botocore.exceptions import ClientError, NoCredentialsError, ProfileNotFound

# ── Config ─────────────────────────────────────────────────────────────────────
REGION        = "us-east-1"
STALE_DAYS    = 30
OUTPUT_FORMAT = "table"      # "table" | "json"
SKIP_PROFILES = {"default"}
# ───────────────────────────────────────────────────────────────────────────────


def get_profiles() -> list[str]:
    cfg = configparser.ConfigParser()
    cfg.read(os.path.expanduser("~/.aws/config"))
    profiles = []
    for section in cfg.sections():
        name = section.removeprefix("profile ").strip()
        if name not in SKIP_PROFILES and name.isdigit():
            profiles.append(name)
    return profiles


def list_all_environments(c9) -> list[dict]:
    ids = []
    for page in c9.get_paginator("list_environments").paginate():
        ids.extend(page.get("environmentIds", []))
    if not ids:
        return []
    envs = []
    for i in range(0, len(ids), 25):
        resp = c9.describe_environments(environmentIds=ids[i:i+25])
        envs.extend(resp.get("environments", []))
    return envs


def last_access_from_memberships(c9, env_id: str) -> datetime | None:
    try:
        times = []
        for page in c9.get_paginator("describe_environment_memberships").paginate(environmentId=env_id):
            for m in page.get("memberships", []):
                la = m.get("lastAccess")
                if la is None:
                    continue
                if isinstance(la, (int, float)):
                    times.append(datetime.fromtimestamp(la, tz=timezone.utc))
                elif isinstance(la, datetime):
                    times.append(la if la.tzinfo else la.replace(tzinfo=timezone.utc))
        return max(times) if times else None
    except ClientError:
        return None


def last_access_from_cloudtrail(ct, env_id: str) -> datetime | None:
    try:
        start = datetime.now(timezone.utc) - timedelta(days=90)
        resp = ct.lookup_events(
            LookupAttributes=[{"AttributeKey": "ResourceName", "AttributeValue": env_id}],
            StartTime=start,
            MaxResults=10,
        )
        events = resp.get("Events", [])
        return events[0]["EventTime"] if events else None
    except ClientError:
        return None


def days_since(dt: datetime | None) -> int | None:
    if dt is None:
        return None
    return (datetime.now(timezone.utc) - dt).days


def extract_user_id(owner_arn: str) -> str:
    """
    Extracts the user/role name from an ARN.
    arn:aws:iam::123456789012:user/jsmith          → jsmith
    arn:aws:sts::123456789012:assumed-role/r/jsmith → jsmith
    """
    part = owner_arn.split(":")[-1]          # e.g. "user/jsmith" or "assumed-role/role/jsmith"
    #return part.split("/")[-1]               # last segment is always the username
    return part.replace("assumed-role/","")


def audit_profile(profile: str) -> list[dict] | str:
    try:
        session = boto3.Session(profile_name=profile, region_name=REGION)
        c9 = session.client("cloud9")
        ct = session.client("cloudtrail")
        envs = list_all_environments(c9)
    except ProfileNotFound:
        return "profile not found"
    except NoCredentialsError:
        return "no credentials"
    except ClientError as e:
        code = e.response["Error"]["Code"]
        if code in ("AccessDeniedException", "UnauthorizedException", "InvalidClientTokenId"):
            return f"access denied ({code})"
        raise

    rows = []
    for env in envs:
        env_id    = env["id"]
        owner_arn = env.get("ownerArn", "")

        last_dt = last_access_from_memberships(c9, env_id)
        source  = "membership"
        if last_dt is None:
            last_dt = last_access_from_cloudtrail(ct, env_id)
            source  = "cloudtrail" if last_dt else "unknown"

        age = days_since(last_dt)
        rows.append({
            "profile":     profile,
            "env_id":      env_id,
            "name":        env.get("name", ""),
            "type":        env.get("type", ""),
            "status":      env.get("lifecycle", {}).get("status", ""),
            "owner_arn":   owner_arn,
            "user_id":     extract_user_id(owner_arn),
            "team_id":     profile,              # profile == TEAMID by convention
            "last_access": last_dt.strftime("%Y-%m-%d %H:%M UTC") if last_dt else "never/unknown",
            "days_since":  age,                  # int or None
            "source":      source,
            "stale":       age is not None and age > STALE_DAYS,
        })
    return rows


# ── Formatting ─────────────────────────────────────────────────────────────────

STALE_MARKER  = "⚠️ "
FRESH_MARKER  = "   "
UNKNOWN_MARKER = "❓ "

def marker(row: dict) -> str:
    if row["days_since"] is None:
        return UNKNOWN_MARKER
    return STALE_MARKER if row["stale"] else FRESH_MARKER


def print_table(rows: list[dict], skipped: dict[str, str]) -> None:
    if skipped:
        print("Skipped profiles:")
        for p, reason in skipped.items():
            print(f"  ✗  {p}: {reason}")
        print()

    if not rows:
        print("No Cloud9 environments found.")
        return

    cols    = ["profile", "user_id", "name", "type", "status", "days_since", "last_access", "source"]
    headers = ["PROFILE", "USER_ID", "NAME", "TYPE", "STATUS", "DAYS", "LAST ACCESS", "SOURCE"]
    display = [{**r, "days_since": str(r["days_since"]) if r["days_since"] is not None else "unknown"} for r in rows]
    widths  = {c: max(len(h), max(len(str(r[c])) for r in display)) for c, h in zip(cols, headers)}

    sep = "  ".join("-" * widths[c] for c in cols)
    hdr = "  ".join(h.ljust(widths[c]) for c, h in zip(cols, headers))
    print(sep)
    print(hdr)
    print(sep)
    for r, d in sorted(zip(rows, display), key=lambda x: (x[0]["profile"], x[0]["name"])):
        line = "  ".join(str(d[c]).ljust(widths[c]) for c in cols)
        print(marker(r) + line)
    print(sep)

    total   = len(rows)
    stale   = sum(1 for r in rows if r["stale"])
    unknown = sum(1 for r in rows if r["days_since"] is None)
    fresh   = total - stale - unknown
    print(f"\n{FRESH_MARKER} Active : {fresh}   {STALE_MARKER} Stale (>{STALE_DAYS}d): {stale}   {UNKNOWN_MARKER} Unknown: {unknown}   Total: {total}")


def print_jenkins_params(rows: list[dict]) -> None:
    stale = [r for r in rows if r["stale"]]
    if not stale:
        print("\nNo stale environments — nothing to decommission.")
        return

    print(f"\n{'─' * 60}")
    print(f"  JENKINS DESTROY PARAMETERS  ({len(stale)} stale environment(s))")
    print(f"{'─' * 60}")

    cols    = ["PROFILE", "USER_ID", "TEAM_ID", "ENV_ID", "DAYS_STALE"]
    col_map = {"PROFILE": "profile", "USER_ID": "user_id", "TEAM_ID": "team_id",
               "ENV_ID": "env_id", "DAYS_STALE": "days_since"}
    widths  = {c: max(len(c), max(len(str(r[col_map[c]])) for r in stale)) for c in cols}

    hdr = "  ".join(c.ljust(widths[c]) for c in cols)
    sep = "  ".join("-" * widths[c] for c in cols)
    print(sep)
    print(hdr)
    print(sep)
    for r in sorted(stale, key=lambda x: (-x["days_since"], x["profile"])):
        vals = {
            "PROFILE":    r["profile"],
            "USER_ID":    r["user_id"],
            "TEAM_ID":    r["team_id"],
            "ENV_ID":     r["env_id"],
            "DAYS_STALE": str(r["days_since"]),
        }
        print("  ".join(vals[c].ljust(widths[c]) for c in cols))
    print(sep)
    print("\nTo trigger destroy for a single environment:")
    eg = stale[0]
    print(f"  jenkins build cloud9-destroy -p USERID={eg['user_id']} TEAMID={eg['team_id']}\n")


def main():
    profiles = get_profiles()
    if not profiles:
        print("No profiles found in ~/.aws/config")
        return

    print(f"Found {len(profiles)} profile(s): {', '.join(sorted(profiles))}")
    print(f"Scanning Cloud9 in {REGION} for each...\n")

    all_rows: list[dict] = []
    skipped:  dict[str, str] = {}

    for profile in sorted(profiles):
        result = audit_profile(profile)
        if isinstance(result, str):
            print(f"  ✗  [{profile}] {result}")
            skipped[profile] = result
        else:
            print(f"  ✓  [{profile}] {len(result)} environment(s)")
            all_rows.extend(result)

    print()

    if OUTPUT_FORMAT == "json":
        stale = [r for r in all_rows if r["stale"]]
        print(json.dumps({
            "summary":    all_rows,
            "to_destroy": [{"PROFILE": r["profile"], "USER_ID": r["user_id"],
                            "TEAM_ID": r["team_id"], "ENV_ID": r["env_id"],
                            "DAYS_STALE": r["days_since"]} for r in stale],
            "skipped":    skipped,
        }, indent=2, default=str))
    else:
        print_table(all_rows, skipped)
        print_jenkins_params(all_rows)


if __name__ == "__main__":
    main()



#########################################

------------  -------------------------------------------------------------------------------------------------------------  ------------  --------------------------------  ----------
PROFILE       USER_ID                                                                                                        TEAM_ID       ENV_ID                DAYS_STALE
------------  -------------------------------------------------------------------------------------------------------------  ------------  --------------------------------  ----------
475117487119  GIO_A3_DeveloperRole/Vilas.Mamidyala@oig.hhs.gov                                                               475117487119  83fc63bd232345c5be4dd2e118291daa  1537
475117487119  ADFS-Tier2/Suseendran.Natarajan@oig.hhs.gov                                                                    475117487119  bb59cede79dd45508c3c6fa4c1d71446  1438
213141505949  ADFS-Tier2/Kaitlin.Devine@oig.hhs.gov                                                                          213141505949  4c2fdd936b6e48438be0be348b848065  1231
213141505949  ADFS-Tier2/Eric.Schiller@oig.hhs.gov                                                                           213141505949  8b34512fc61644e3a9d538a7bd1baa8a  1026
973222108556  OIGDW-DeveloperRole/Christopher.Arsenault@oig.hhs.gov                                                          973222108556  d015fe9698d94cd9a410c4590dffe409  1000
213141505949  ADFS-Tier2/Aaron.Anderson@oig.hhs.gov                                                                          213141505949  5c03da1b073544ea945a368cbebe3bb0  858

