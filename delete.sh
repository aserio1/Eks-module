#!/usr/bin/env bash
set -euo pipefail

INPUT_FILE="${1:-delete_cloud9.txt}"
MODE="${2:-dry-run}"
REGION="us-east-1"

DELETED=0
FAILED=0
NOT_FOUND=0
PROCESSED=0
SKIPPED=0

if [[ ! -f "$INPUT_FILE" ]]; then
  echo "File not found: $INPUT_FILE"
  exit 1
fi

echo "INPUT_FILE=$INPUT_FILE"
echo "MODE=$MODE"
echo "REGION=$REGION"
echo

while read -r PROFILE USER_ID TEAM_ID ENV_ID DAYS_STALE; do
  # Skip blank lines, comments, headers, separators
  [[ -z "${PROFILE:-}" ]] && continue
  [[ "$PROFILE" == \#* ]] && continue
  [[ "$PROFILE" == "PROFILE" ]] && continue
  [[ "$PROFILE" == "------------" ]] && continue
  [[ ! "$PROFILE" =~ ^[0-9]{12}$ ]] && continue

  PROCESSED=$((PROCESSED + 1))

  echo "============================================================"
  echo "PROFILE=$PROFILE"
  echo "USER_ID=$USER_ID"
  echo "TEAM_ID=$TEAM_ID"
  echo "ENV_ID=$ENV_ID"
  echo "DAYS_STALE=$DAYS_STALE"

  if aws cloud9 describe-environments \
    --profile "$PROFILE" \
    --region "$REGION" \
    --environment-ids "$ENV_ID" \
    --query 'environments[0].id' \
    --output text 2>/dev/null | grep -q "$ENV_ID"; then

    echo "Cloud9 exists: $ENV_ID"

    if [[ "$MODE" == "--execute" ]]; then
      echo "Deleting Cloud9 environment: $ENV_ID"

      if aws cloud9 delete-environment \
        --profile "$PROFILE" \
        --region "$REGION" \
        --environment-id "$ENV_ID"; then

        echo "✓ Delete request accepted."
        DELETED=$((DELETED + 1))
      else
        echo "✗ Delete failed."
        FAILED=$((FAILED + 1))
      fi
    else
      echo "Dry-run: would delete Cloud9 environment: $ENV_ID"
      SKIPPED=$((SKIPPED + 1))
    fi

  else
    echo "Cloud9 environment does not exist: $ENV_ID"
    NOT_FOUND=$((NOT_FOUND + 1))
  fi

  echo

done < "$INPUT_FILE"

echo "============================================================"
echo "Cloud9 Delete Summary"
echo "============================================================"
echo "Processed                : $PROCESSED"
echo "Delete requests accepted : $DELETED"
echo "Dry-run skipped          : $SKIPPED"
echo "Not found                : $NOT_FOUND"
echo "Failed                   : $FAILED"
echo "Mode                     : $MODE"
echo "============================================================"



#######DELETE 
475117487119  GIO_A3_DeveloperRole/Vilas.Mamidyala@oig.hhs.gov  475117487119  83fc63bd232345c5be4dd2e118291daa  1544
475117487119  ADFS-Tier2/Suseendran.Natarajan@oig.hhs.gov       475117487119  bb59cede79dd45508c3c6fa4c1d71446  1445
213141505949  ADFS-Tier2/Christopher.Arsenault@oig.hhs.gov      213141505949  5408803941a6403a9e5c7bddfe5f22b2  71
