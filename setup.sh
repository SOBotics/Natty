#!/usr/bin/env bash

mkdir data
mkdir logs
mkdir aulogs
mkdir properties

touch properties/login.properties

touch logs/autoflagged.txt
touch logs/botUsers.txt
touch logs/feedback.txt
touch logs/fullReports.txt
touch logs/output.csv
touch logs/reports.txt

touch logs/sentinelReports.txt
touch aulogs/autoflagged.txt
touch aulogs/botUsers.txt
touch aulogs/feedback.txt
touch aulogs/fullReports.txt
touch aulogs/output.csv
touch aulogs/reports.txt
touch aulogs/sentinelReports.txt

touch data/BlackListedUsers.txt
touch data/CheckUsers.txt
touch data/IntelligentBlacklist.json
touch data/Salutations.txt
touch data/BlackListedWords.txt
touch data/FeatureRequests.txt
touch data/OptedInUsersList.txt
touch data/WhiteListedWords.txt

echo  "apikey=
autoflagkey=
autoflagtoken=
userid=
username=
location=
email=
password=
sentinelKey=
sentinelApiKey=
fmsPath=
fmsUrl=
feedMsg=" > properties/login.properties

python3 setup/downloadData.py
python3 setup/requestData.py

mvn clean
mvn packages
cp target/natty-v4.jar .