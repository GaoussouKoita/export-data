#!/bin/sh
jour=`date -u +%Y%m%d`
mkdir -p /usr/local/app/logs/
chmod +x /usr/local/app/logs/
java -jar /usr/local/app/my-app.jar >> /usr/local/app/logs/my-app_log_$jour.log