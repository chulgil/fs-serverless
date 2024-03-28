npm install
zip -r barodreamConsumer.zip .
aws lambda update-function-configuration --function-name barodream-consumer --handler barodreamConsumer.handler --timeout 30 --description aws:states:opt-out
aws lambda update-function-code --function-name barodream-consumer --zip-file fileb://barodreamConsumer.zip --no-paginate --no-cli-pager
rm barodreamConsumer.zip
