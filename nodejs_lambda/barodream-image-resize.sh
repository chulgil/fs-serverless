npm install
zip -r barodreamImageResize.zip .
aws lambda update-function-configuration --function-name barodream-image-resize --handler barodreamImageResize.handler --timeout 60 --description aws:states:opt-out
aws lambda update-function-code --function-name barodream-image-resize --zip-file fileb://barodreamImageResize.zip --no-paginate --no-cli-pager
rm barodreamImageResize.zip
