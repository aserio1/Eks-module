#!/usr/bin/env bash
set -euo pipefail

PROFILE="$1"
USER_ID="$2"
ENV_ID="$3"
EXECUTE="${4:-dry-run}"

REGION="us-east-1"
BUCKET="imac-prod-${PROFILE}-tf-state-hhsoig"
LOCK_TABLE="imac-prod-${PROFILE}-tf-lock-hhsoig"

echo "PROFILE=$PROFILE"
echo "USER_ID=$USER_ID"
echo "ENV_ID=$ENV_ID"

# 1. Check if Cloud9 environment exists
if aws cloud9 describe-environments \
  --profile "$PROFILE" \
  --region "$REGION" \
  --environment-ids "$ENV_ID" \
  --query 'environments[0].id' \
  --output text 2>/dev/null | grep -q "$ENV_ID"; then
  EXISTS="yes"
  echo "Cloud9 environment exists: $ENV_ID"
else
  EXISTS="no"
  echo "Cloud9 environment does not exist: $ENV_ID"
fi

# 2. Find Terraform state key for USER_ID
STATE_KEY=$(aws s3api list-objects-v2 \
  --bucket "$BUCKET" \
  --profile "$PROFILE" \
  --region "$REGION" \
  --query "Contents[?contains(Key, \`${USER_ID}\`)].Key | [0]" \
  --output text)

if [[ -z "$STATE_KEY" || "$STATE_KEY" == "None" ]]; then
  echo "Terraform state does not exist for USER_ID: $USER_ID"
  exit 0
fi

echo "Terraform state key: $STATE_KEY"

# 3. Init Terraform backend
terraform init -reconfigure \
  -backend-config="region=${REGION}" \
  -backend-config="bucket=${BUCKET}" \
  -backend-config="dynamodb_table=${LOCK_TABLE}" \
  -backend-config="key=${STATE_KEY}"

# 4. Find resources in state matching Cloud9 ENV_ID
MATCHING_RESOURCES=$(terraform state list | while read -r addr; do
  if terraform state show "$addr" 2>/dev/null | grep -q "$ENV_ID"; then
    echo "$addr"
  fi
done)

if [[ -z "$MATCHING_RESOURCES" ]]; then
  echo "No Terraform state resources found for ENV_ID: $ENV_ID"
  exit 0
fi

echo "Matching Terraform resources:"
echo "$MATCHING_RESOURCES"

if [[ "$EXECUTE" != "--execute" ]]; then
  echo
  echo "Dry-run only. To destroy/remove, run:"
  echo "$0 '$PROFILE' '$USER_ID' '$ENV_ID' --execute"
  exit 0
fi

# 5. If Cloud9 exists, destroy it through Terraform
if [[ "$EXISTS" == "yes" ]]; then
  echo "Running terraform destroy for matching resources..."

  TARGET_ARGS=()
  while read -r addr; do
    TARGET_ARGS+=("-target=$addr")
  done <<< "$MATCHING_RESOURCES"

  terraform plan -destroy "${TARGET_ARGS[@]}" -out=tf-destroy-plan
  terraform apply -auto-approve tf-destroy-plan
else
  echo "Skipping terraform destroy because Cloud9 environment does not exist."
fi

# 6. Remove leftovers from state
echo "Cleaning leftover resources from Terraform state..."

while read -r addr; do
  if terraform state show "$addr" >/dev/null 2>&1; then
    echo "Removing from state: $addr"
    terraform state rm "$addr"
  fi
done <<< "$MATCHING_RESOURCES"

echo "Done."








###############DELETE ###################
########################################
#!/usr/bin/env bash
set -euo pipefail

PROFILE="${1:-}"
USER_ID="${2:-}"
ENV_ID="${3:-}"
EXECUTE="${4:-dry-run}"

REGION="us-east-1"
BUCKET="imac-prod-${PROFILE}-tf-state-hhsoig"
LOCK_TABLE="imac-prod-${PROFILE}-tf-lock-hhsoig"

if [[ -z "$PROFILE" || -z "$USER_ID" || -z "$ENV_ID" ]]; then
  echo "Usage:"
  echo "$0 <PROFILE> <USER_ID> <ENV_ID> [--execute]"
  exit 1
fi

echo "PROFILE=$PROFILE"
echo "USER_ID=$USER_ID"
echo "ENV_ID=$ENV_ID"
echo "MODE=$EXECUTE"
echo

############################################
# 1. Check and delete Cloud9 environment
############################################

if aws cloud9 describe-environments \
  --profile "$PROFILE" \
  --region "$REGION" \
  --environment-ids "$ENV_ID" \
  --query 'environments[0].id' \
  --output text 2>/dev/null | grep -q "$ENV_ID"; then

  echo "Cloud9 environment exists: $ENV_ID"

  if [[ "$EXECUTE" == "--execute" ]]; then
    echo "Deleting Cloud9 environment: $ENV_ID"

    aws cloud9 delete-environment \
      --profile "$PROFILE" \
      --region "$REGION" \
      --environment-id "$ENV_ID"

    echo "Delete requested for Cloud9 environment: $ENV_ID"
  else
    echo "Dry-run: would delete Cloud9 environment: $ENV_ID"
  fi

else
  echo "Cloud9 environment does not exist: $ENV_ID"
fi

echo

############################################
# 2. Find Terraform state key
############################################

echo "Looking for Terraform state in bucket: $BUCKET"

STATE_KEY=$(aws s3api list-objects-v2 \
  --bucket "$BUCKET" \
  --profile "$PROFILE" \
  --region "$REGION" \
  --query "Contents[?contains(Key, \`${USER_ID}\`)].Key | [0]" \
  --output text 2>/dev/null || true)

if [[ -z "$STATE_KEY" || "$STATE_KEY" == "None" ]]; then
  echo "Terraform state does not exist for USER_ID: $USER_ID"
  echo "Done."
  exit 0
fi

echo "Terraform state key: $STATE_KEY"
echo

############################################
# 3. Initialize Terraform backend
############################################

terraform init -reconfigure \
  -backend-config="region=${REGION}" \
  -backend-config="bucket=${BUCKET}" \
  -backend-config="dynamodb_table=${LOCK_TABLE}" \
  -backend-config="key=${STATE_KEY}"

echo

############################################
# 4. Find resources matching ENV_ID
############################################

echo "Searching Terraform state for resources matching ENV_ID: $ENV_ID"

MATCHING_RESOURCES=$(terraform state list | while read -r addr; do
  if terraform state show "$addr" 2>/dev/null | grep -q "$ENV_ID"; then
    echo "$addr"
  fi
done)

if [[ -z "$MATCHING_RESOURCES" ]]; then
  echo "No Terraform state resources found for ENV_ID: $ENV_ID"
  echo "Done."
  exit 0
fi

echo "Matching Terraform resources:"
echo "$MATCHING_RESOURCES"
echo

############################################
# 5. Remove matching resources from state
############################################

if [[ "$EXECUTE" != "--execute" ]]; then
  echo "Dry-run only. Would remove these resources from Terraform state:"
  echo "$MATCHING_RESOURCES"
  echo
  echo "To actually delete Cloud9 and clean state, run:"
  echo "$0 '$PROFILE' '$USER_ID' '$ENV_ID' --execute"
  exit 0
fi

echo "Removing matching resources from Terraform state..."

while read -r addr; do
  if terraform state show "$addr" >/dev/null 2>&1; then
    echo "Removing from state: $addr"
    terraform state rm "$addr"
  else
    echo "Resource already removed from state: $addr"
  fi
done <<< "$MATCHING_RESOURCES"

echo
echo "Done."






#############################
DRY RUN 
################################
chmod +x destroy_cloud9_instance.sh

./destroy_cloud9_instance.sh \
  475117487119 \
  'GIO_A3_DeveloperRole/Vilas.Mamidyala@oig.hhs.gov' \
  83fc63bd232345c5be4dd2e118291daa


##################################
Execute
##################################
./destroy_cloud9_instance.sh \
  475117487119 \
  'GIO_A3_DeveloperRole/Vilas.Mamidyala@oig.hhs.gov' \
  83fc63bd232345c5be4dd2e118291daa \
  --execute


#######################
aws cloud9 describe-environments \
  --profile 213141505949 \
  --region us-east-1 \
  --environment-ids a7e73a20df114b07985db39911b6acf7



########STALE#######################
#!/usr/bin/env bash
set -euo pipefail

INPUT_FILE="${1:-stale_cloud9.txt}"
MODE="${2:-dry-run}"
REGION="us-east-1"

if [[ ! -f "$INPUT_FILE" ]]; then
  echo "File not found: $INPUT_FILE"
  exit 1
fi

echo "INPUT_FILE=$INPUT_FILE"
echo "MODE=$MODE"
echo

while read -r PROFILE USER_ID TEAM_ID ENV_ID DAYS_STALE; do
  # Skip headers, separators, blank lines, comments
  [[ -z "${PROFILE:-}" ]] && continue
  [[ "$PROFILE" == "PROFILE" ]] && continue
  [[ "$PROFILE" == "------------" ]] && continue
  [[ "$PROFILE" == \#* ]] && continue
  [[ ! "$PROFILE" =~ ^[0-9]{12}$ ]] && continue

  echo "============================================================"
  echo "PROFILE=$PROFILE"
  echo "USER_ID=$USER_ID"
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

      aws cloud9 delete-environment \
        --profile "$PROFILE" \
        --region "$REGION" \
        --environment-id "$ENV_ID"

      echo "Waiting for deletion..."

      for i in {1..30}; do
        if aws cloud9 describe-environments \
          --profile "$PROFILE" \
          --region "$REGION" \
          --environment-ids "$ENV_ID" \
          --query 'environments[0].id' \
          --output text 2>/dev/null | grep -q "$ENV_ID"; then
          echo "Still deleting... attempt $i/30"
          sleep 10
        else
          echo "Deleted: $ENV_ID"
          break
        fi
      done
    else
      echo "Dry-run: would delete Cloud9 environment: $ENV_ID"
    fi

  else
    echo "Cloud9 environment does not exist: $ENV_ID"
  fi

  echo

done < "$INPUT_FILE"

echo "Done."



##################
chmod +x bulk_delete_cloud9.sh

./bulk_delete_cloud9.sh stale_cloud9.txt

./bulk_delete_cloud9.sh stale_cloud9.txt --execute




============================================================
PROFILE=475117487119
USER_ID=GIO_A3_DeveloperRole/Vilas.Mamidyala@oig.hhs.gov
ENV_ID=83fc63bd232345c5be4dd2e118291daa
DAYS_STALE=1537
Cloud9 exists: 83fc63bd232345c5be4dd2e118291daa
Deleting Cloud9 environment: 83fc63bd232345c5be4dd2e118291daa
Waiting for deletion...
Still deleting... attempt 1/30
Still deleting... attempt 2/30
Still deleting... attempt 3/30
Still deleting... attempt 4/30
Still deleting... attempt 5/30
Still deleting... attempt 6/30
Still deleting... attempt 7/30
Still deleting... attempt 8/30
Still deleting... attempt 9/30
Still deleting... attempt 10/30
Still deleting... attempt 11/30
Terminated
sh-5.2$ sudo docker exec -it jenkins bash
[root@27c06bc250ca jenkins]# su jenkins
bash-5.1$ ls
Miniconda3-py310_24.7.1-0-Linux-x86_64.sh  bulk_delete_cloud9.sh  destroy_cloud9_instance.sh  python310.env.yml  register.sh       workspace
agent.jar                                  caches                 jenkins-cli.jar             python312.env.yml  remoting
agent_ssh.pem                              cred.xml               node.xml                    python313.env.yml  remoting.jar
anaconda3                                  cred_replaced.xml      node_replaced.xml           python314.env.yml  stale_cloud9.txt
bash-5.1$ ./bulk_delete_cloud9.sh stale_cloud9.txt --execute
INPUT_FILE=stale_cloud9.txt
MODE=--execute

============================================================
PROFILE=475117487119
USER_ID=GIO_A3_DeveloperRole/Vilas.Mamidyala@oig.hhs.gov
ENV_ID=83fc63bd232345c5be4dd2e118291daa
DAYS_STALE=1537
Cloud9 exists: 83fc63bd232345c5be4dd2e118291daa
Deleting Cloud9 environment: 83fc63bd232345c5be4dd2e118291daa
Waiting for deletion...
Still deleting... attempt 1/30
Still deleting... attempt 2/30
Still deleting... attempt 3/30
Still deleting... attempt 4/30
Still deleting... attempt 5/30
Still deleting... attempt 6/30
Still deleting... attempt 7/30
Still deleting... attempt 8/30
Still deleting... attempt 9/30
Still deleting... attempt 10/30
Still deleting... attempt 11/30
Still deleting... attempt 12/30
Still deleting... attempt 13/30
Still deleting... attempt 14/30
Still deleting... attempt 15/30
Still deleting... attempt 16/30
Still deleting... attempt 17/30
Still deleting... attempt 18/30
Still deleting... attempt 19/30
Still deleting... attempt 20/30
Still deleting... attempt 21/30
Still deleting... attempt 22/30
Still deleting... attempt 23/30
Still deleting... attempt 24/30
Still deleting... attempt 25/30
Still deleting... attempt 26/30
Still deleting... attempt 27/30
Still deleting... attempt 28/30
Still deleting... attempt 29/30
Still deleting... attempt 30/30

============================================================
PROFILE=475117487119
USER_ID=ADFS-Tier2/Suseendran.Natarajan@oig.hhs.gov
ENV_ID=bb59cede79dd45508c3c6fa4c1d71446
DAYS_STALE=1438
Cloud9 exists: bb59cede79dd45508c3c6fa4c1d71446
Deleting Cloud9 environment: bb59cede79dd45508c3c6fa4c1d71446
Waiting for deletion...
Still deleting... attempt 1/30
Still deleting... attempt 2/30
Still deleting... attempt 3/30
Still deleting... attempt 4/30
