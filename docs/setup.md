# How can I run the bot under my account?

### Prerequisites: 

 - Java 9+ 
 - Maven 
 - Python3 (for the automatic setup, else you can do it manually)
 - Tomcat 7+ (for Fake MetaSmoke - FMS,  or a ruby stack, if you want to use Sentinel. Let's stick with FMS for now)

### Steps for the automated setup: 

 1. Clone the latest version of the repository using `git`. (Use the [Nattyv3](https://github.com/SOBotics/Natty/tree/v3) branch if you would like to run a older version of the file). 

        git clone https://github.com/SOBotics/Natty.git
 2. **Make changes to the source code about the places where you want it to run!**. This is really important. As of now, any development version will just run in Sobotics and ignores all other rooms. You can change this by creating a new [`BotRoom`](https://github.com/SOBotics/Natty/blob/master/src/main/java/in/bhargavrao/stackoverflow/natty/roomdata/BotRoom.java) and configuring it as to what it needs to read, what it needs to display, where it needs to send, etc. (Here is the place where we set Natty to look only for NAAs on new posts and not all posts). 
 3.  Run `sh setup.sh`. It will ask you for a set of data and then create the jar which you can run. 

### Steps for manual setup (optional): 

 1. Follow the steps 1. and 2. from the automatic setup.
 2. Run `mvn clean package`. A folder `/target` will be created inside which a `.jar` file will be present. This is the complete bot code which you need to run.
 3.  At the same level of the `.jar` file which you created. Create the following structure: 


            |--- data
            |   |--- BlackListedUsers.txt
            |   |--- BlackListedWords.txt
            |   |--- CheckUsers.txt
            |   |--- FeatureRequests.txt
            |   |--- OptedInUsersList.txt
            |   |--- Salutations.txt
            |    --- WhiteListedWords.txt
            |--- logs
            |   |--- fullReports.txt
            |   |--- output.csv
            |   |--- reports.txt
            |    --- sentinelReports.txt
            |--- properties
            |    --- login.properties


   if you are tracking for AU as well, then you'll need to create a `aulogs` folder structure:


        |--- aulogs
        |   |--- fullReports.txt
        |   |--- output.csv
        |   |--- reports.txt
        |    --- sentinelReports.txt

 4. Pre-populating the blacklist/whitelist/salutation data : This is a bit of a screwed up task because you will need to come up with a list of all the keywords. Fortunately the NAPI provides an [end point using which we can query the data.][1] We will need to get the data from there. 
 5. Leave the CheckUsers, BlackListedUsers, FeatureRequests and OptedInUsersList empty. 
 6. Leave the entire thing under `/logs` empty.
 7. In `login.properties` add: 

        apikey=SE_API_KEY
        apitoken=SE_AUTH_TOKEN_ASSOCIATED
        userid=USER_ID_OF_THE_BOT
        username=USERNAME_OF_THE_BOT
        location=YOUR_COMPUTER_NAME
        email=EMAIL_ID
        password=PASSWORD
        sentinelKey=REGISTERED_KEY_FOR_SENTINEL
        sentinelApiKey=REGISTERED_KEY_TO_USE_SENTINEL_APIS
        fmsPath=PATH_TO_THE_END_DIRECTORY_OF_FMS
        fmsUrl=LINK_TO_THE_FMS_DOMAIN
        feedMsg=*Feed Kyll here*

  if you are running a local instance, you will not need the sentinel keys and the autoflag keys. 

### Setting up FMS - A dashboard for your bot

At first, I had created a MVP and managed to get some sort of interface where <10k could see a post, even if it was deleted. The FMS was that. After that ArtOfCode wrote a nice Sentinel. However Sentinel might fail sometimes and we'd be losing a lot of data (once it was down for 2 months). So FMS still stands as a backup. 

Now for the disclaimer, **You can ignore this completely if you don't want a dashboard**. FMS is just a visual frontend for the report and serves no other purpose. 

Setting up FMS is simple: 

  - Download Tomcat9 (or any other previous 7+ version) zip from the [downloads page of Apache](https://tomcat.apache.org). 
  - Run `unzip apache-tomcat-9.v.v.zip` 
  - Run `mkdir apache-tomcat-9.v.v/webapps/ROOT/Natty`
  - Run `sh tomcat/bin/startup.sh`. Tomcat would now be up and listening at Port 8080. 
  - Add `apache-tomcat-9.v.v/webapps/ROOT/Natty/` (or use the fully qualified path) as the value for the parameter `fmsPath` and `http://localhost:8080/Natty` for the parameter `fmsUrl` in the login.properties file. 


### Running the bot 

  - This is simple, just do `java -jar natty.jar` and it will start to execute. 





  [1]: https://github.com/SOBotics/NAPI
