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
PROFILE       USER_ID                                                                                                        TEAM_ID       ENV_ID                DAYS_STALE
------------  -------------------------------------------------------------------------------------------------------------  ------------  --------------------------------  ----------
475117487119  GIO_A3_DeveloperRole/Vilas.Mamidyala@oig.hhs.gov                                                               475117487119  83fc63bd232345c5be4dd2e118291daa  1537
475117487119  ADFS-Tier2/Suseendran.Natarajan@oig.hhs.gov                                                                    475117487119  bb59cede79dd45508c3c6fa4c1d71446  1438
213141505949  ADFS-Tier2/Kaitlin.Devine@oig.hhs.gov                                                                          213141505949  4c2fdd936b6e48438be0be348b848065  1231
213141505949  ADFS-Tier2/Eric.Schiller@oig.hhs.gov                                                                           213141505949  8b34512fc61644e3a9d538a7bd1baa8a  1026
973222108556  OIGDW-DeveloperRole/Christopher.Arsenault@oig.hhs.gov                                                          973222108556  d015fe9698d94cd9a410c4590dffe409  1000
213141505949  ADFS-Tier2/Aaron.Anderson@oig.hhs.gov                                                                          213141505949  5c03da1b073544ea945a368cbebe3bb0  858
213141505949  ADFS-Tier2-TRI/Jaclyn.Hammond@oig.hhs.gov                                                                      213141505949  a7e73a20df114b07985db39911b6acf7  851
213141505949  ADFS-Tier2/Katie.Rose@oig.hhs.gov                                                                              213141505949  c27922cb671549dd81c7bd12a11df0ab  848
213141505949  ADFS-Tier2/Mara.Werner@oig.hhs.gov                                                                             213141505949  9153c04485504f1388be30e1659b55d7  803
213141505949  ADFS-Tier2/Adrienne.Barge@oig.hhs.gov                                                                          213141505949  14e89c4102464bca85fba3e514dff423  792
213141505949  ADFS-Tier2/Xianci.Tang@oig.hhs.gov                                                                             213141505949  3a87eeeb593f45e89a86bfe36eee2385  776
757265181315  prod_developer_role_sie_dev/Cameron.Hopkin@oig.hhs.gov                                                         757265181315  b6943f45429b4c69836f51e6c0a7677c  776
213141505949  OIGDW-DeveloperRole/Martin.Vystavil@oig.hhs.gov                                                                213141505949  b982af787df249d99d33afd451f0c799  770
213141505949  ADFS-Tier2/William.Zichos@oig.hhs.gov                                                                          213141505949  c631fb11ddf1464c9b27b6f6c4250d60  756
213141505949  ADFS-Tier2/David.Willson@oig.hhs.gov                                                                           213141505949  46b26dfc7ab9476e8117a742cdd9e346  756
213141505949  ADFS-Tier2/Brandon.Rusk@oig.hhs.gov                                                                            213141505949  50496cab8884408fbaea69818aa75609  756
213141505949  ADFS-Tier2/Erin.Mahagan@oig.hhs.gov                                                                            213141505949  c42fe98ff09d452888d8446f29733dd8  728
213141505949  ADFS-Tier2/Saheed.Omidiran@oig.hhs.gov                                                                         213141505949  b0b3e4d011f74664bcf09ee9c0943042  686
213141505949  ADFS-Tier2/Indira.Kasandula@oig.hhs.gov                                                                        213141505949  617cddd63cec417eb7c742aebf159b64  679
730335313798  AWSReservedSSO_hhs-oig-analyst-ides-tier2_22c746b1ab4a2fac/Christopher.Arsenault@oig.hhs.gov                   730335313798  bcc192aebf654c03b78a31fd79d4e357  664
730335313798  prod_developer_role_ides_dev/Christopher.Arsenault@oig.hhs.gov                                                 730335313798  44331e1608114cf2947f6222b7fbba08  651
757265181315  AWSReservedSSO_hhs-oig-cloud-analytics-tier2_42c1ddc1e750dac5/Christopher.Arsenault@oig.hhs.gov                757265181315  d39a934a6dcf44b3b21698d0a5cf9a48  589
757265181315  AWSReservedSSO_hhs-oig-cloud-analytics-tier2_42c1ddc1e750dac5/Devin.Aberasturi@oig.hhs.gov                     757265181315  5bc2eb5f052f4e45a49e2ff11f717bff  573
757265181315  AWSReservedSSO_hhs-oig-cloud-analytics-tier2_42c1ddc1e750dac5/Anna.Xu@oig.hhs.gov                              757265181315  3df2e9dc14a04f0c8397d7ff0e2cd292  573
730335313798  AWSReservedSSO_hhs-oig-analyst-ides-tier2_22c746b1ab4a2fac/Ben.Horton@oig.hhs.gov                              730335313798  168d38dcd13444cfbc85a06f78bd0260  564
757265181315  AWSReservedSSO_hhs-oig-cloud-analytics-tier2_42c1ddc1e750dac5/Cameron.Hopkin@oig.hhs.gov                       757265181315  fdbaf96a60434b829250f931e796067d  557
213141505949  ADFS-Tier2/Spencer.Scott@oig.hhs.gov                                                                           213141505949  67cf344bf9e34f69922ae54e4fb4e166  535
730335313798  AWSReservedSSO_hhs-oig-analyst-ides-tier2_22c746b1ab4a2fac/Raman.Bhasker@oig.hhs.gov                           730335313798  d5d275b0793247f68a72ab9dfc38ffaa  517
757265181315  AWSReservedSSO_hhs-oig-cloud-analytics-tier2_42c1ddc1e750dac5/Neeraj.Sirdeshmukh@oig.hhs.gov                   757265181315  172a4e32a83542ecb6d6ab155ad0814a  517
730335313798  AWSReservedSSO_hhs-oig-analyst-ides-tier2_22c746b1ab4a2fac/Cosmina.Chindris@oig.hhs.gov                        730335313798  e93a6504860945b9b9c7e84a858c274a  516
213141505949  ADFS-Tier2/Graham.Kerster@oig.hhs.gov                                                                          213141505949  360e688c9adf47909f03c41a86afd14c  489
757265181315  AWSReservedSSO_hhs-oig-cloud-analytics-tier2_42c1ddc1e750dac5/David.Willson@oig.hhs.gov                        757265181315  0d0e7e841ef34b829975573bd2ab2836  488
757265181315  AWSReservedSSO_hhs-oig-cloud-analytics-tier2_42c1ddc1e750dac5/Reginald.Law@oig.hhs.gov                         757265181315  5a21126a55e54594a430d13ef1e82bfa  466
757265181315  AWSReservedSSO_hhs-oig-cloud-analytics-tier2_42c1ddc1e750dac5/Brandon.Rusk@oig.hhs.gov                         757265181315  0833e17b513b4365acd6b77bdff54b0d  444
213141505949  ADFS-Tier2/Vickey.Chen@oig.hhs.gov                                                                             213141505949  972e9f5e15cc4643b6c9bf585ec6504a  430
730335313798  AWSReservedSSO_hhs-oig-analyst-ides-tier2_22c746b1ab4a2fac/Indira.Kasandula@oig.hhs.gov                        730335313798  544fa6c2c4134d70bde31edcd34aac16  414
213141505949  ADFS-Tier2/Gabriel.Price@oig.hhs.gov                                                                           213141505949  bb2d4d3cd74240bba66fb9bf0808a829  395
757265181315  AWSReservedSSO_hhs-oig-cloud-analytics-tier2_42c1ddc1e750dac5/Jamaal.Royal@oig.hhs.gov                         757265181315  1c0e3903f7ed419689c9f5faf1d2d54e  395
213141505949  ADFS-Tier2/Martin.Vystavil@oig.hhs.gov                                                                         213141505949  7f7b0e69ade54b0cadca5650b506f31a  339
757265181315  AWSReservedSSO_hhs-oig-cloud-analytics-tier2_42c1ddc1e750dac5/Saheed.Omidiran@oig.hhs.gov                      757265181315  8d01bd8101614fd68964b10c7b490c2e  321
213141505949  ADFS-Tier2/Andrew.Griffith@oig.hhs.gov                                                                         213141505949  8acfa8413112403fb0435de1f7292d94  319
213141505949  ADFS-Tier2/Cameron.Hopkin@oig.hhs.gov                                                                          213141505949  63c869ffd02d4852beccf39ceebcb221  286
730335313798  AWSReservedSSO_hhs-oig-analyst-ides-tier2_22c746b1ab4a2fac/Claudio.SidiKemvo@oig.hhs.gov                       730335313798  809c3b967e5247cdac78622dd2641a80  267
730335313798  AWSReservedSSO_hhs-oig-analyst-ides-tier2_f7058977ee4e9469/Raman.Bhasker@oig.hhs.gov                           730335313798  35eb47b40ce1403b9a1b059671214493  257
757265181315  AWSReservedSSO_hhs-oig-cloud-analytics-tier2_42c1ddc1e750dac5/Claudio.SidiKemvo@oig.hhs.gov                    757265181315  9d8be49146a447bf8f636cce0d1d36ca  251
213141505949  ADFS-Tier2/Akinwale.Emiabata@oig.hhs.gov                                                                       213141505949  458796ea2a4a47649374f2275a4ab1f0  244
757265181315  AWSReservedSSO_hhs-oig-cloud-analytics-tier2_42c1ddc1e750dac5/Sindhu.Boppudi@oig.hhs.gov                       757265181315  7690bae3eb0b4e91b75afdef3df0381b  236
757265181315  AWSReservedSSO_hhs-oig-cloud-analytics-tier2_42c1ddc1e750dac5/Raman.Bhasker@oig.hhs.gov                        757265181315  116f5b3ae4114cb3829d1f0c170b26c2  229
757265181315  AWSReservedSSO_hhs-oig-cloud-analytics-tier2_ee08d5cc50a1f525/Raman.Bhasker@oig.hhs.gov                        757265181315  62fb081fd89a4d269c959676181c92d4  229
757265181315  AWSReservedSSO_hhs-oig-cloud-analytics-tier2_ee08d5cc50a1f525/Christopher.Arsenault@oig.hhs.gov                757265181315  21d849ae3b1844aba1ee553cec716228  159
213141505949  ADFS-Tier2/Cosmina.Chindris@oig.hhs.gov                                                                        213141505949  4d2b4a66c6d34059a2e8016f1c805a1d  138
757265181315  AWSReservedSSO_hhs-oig-cloud-analytics-tier2_42c1ddc1e750dac5/LekshmiKrishnamandiram.RadhabaiAmma@oig.hhs.gov  757265181315  ffe45f34b78846aebcde3cc4d8966b77  113
213141505949  ADFS-Tier2/Raman.Bhasker@oig.hhs.gov                                                                           213141505949  6991c98913be4fd6bd300592cf98d2c6  85
213141505949  ADFS-Tier2/Christopher.Arsenault@oig.hhs.gov                                                                   213141505949  5408803941a6403a9e5c7bddfe5f22b2  64
757265181315  AWSReservedSSO_hhs-oig-cloud-analytics-tier2_ee08d5cc50a1f525/Neeraj.Sirdeshmukh@oig.hhs.gov                   757265181315  0673edf2d0bb4de49e997600724a2ef4  50
213141505949  ADFS-Tier2/Parmjeet.Kaur@oig.hhs.gov                                                                           213141505949  d57835052dcf41e9a8d8709738a105e2  41
213141505949  ADFS-Tier2/Delaney.Biggins@oig.hhs.gov                                                                         213141505949  6ee1ddbf7c1d418d9af5884603f48ded  40
