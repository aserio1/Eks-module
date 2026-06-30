aws cloudtrail lookup-events \
  --lookup-attributes AttributeKey=EventName,AttributeValue=CreateEnvironmentEC2 \
  --profile 757265181315 \
  --query 'Events[].Username'



aws ec2 describe-instances \
  --profile 757265181315 \
  --filters "Name=tag-key,Values=aws:cloud9:environment" \
  --query 'Reservations[].Instances[].{
      InstanceId:InstanceId,
      Name:Tags[?Key==`Name`]|[0].Value,
      Cloud9Env:Tags[?Key==`aws:cloud9:environment`]|[0].Value,
      State:State.Name,
      PrivateIP:PrivateIpAddress
  }' \
  --output table



aws ec2 describe-instances \
  --profile 757265181315 \
  --filters "Name=tag:aws:cloud9:environment,Values=<environment-id>"
