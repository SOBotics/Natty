# Commands list

The list of all commands are

    addcheckuser    - Adds a new user to the special users list
    addsalute       - Adds a given statement to the list of salutations
    alive           - Returns a test reply to inform that the bot is alive
    blacklist       - Adds a given statement to the list of blacklisted words
    check           - Checks the sanity of a given post
    delete          - Deletes a given comment
    feedback        - Provides feedback on a given post
    fetch           - Returns a list of posts that need feedback
    halp            - Plop, check it out
    help            - Returns information regarding the chatbot
    hi              - Hi, Test it out
    isblacklisted   - Checks if the given statement is blacklisted
    iswhitelisted   - Checks if the given statement is whitelisted
    opt-in          - Notifies the user of the requested posts
    opt-out         - Unnotifies the user. 
    quota           - Returns the remaining API Quota
    reboot          - Reboots the bot
    remind          - Adds a reminder to the list of requests
    rmblacklist     - Removes the given statement from blacklist
    rmreq           - Removes the request from the list of requests
    rmwhitelist     - Removes the given statement from whitelist  
    report          - Reports the mentioned post as a true negative NAA/VLQ
    say             - Echoes the user input
    send            - Sends a mass feedback, see the wiki for more details
    showreqs        - Shows the list of requests
    status          - Returns the status of the bot
    whitelist       - Adds a given statement to the list of whitelisted words
    wishBD          - Wishes the user a very happy birthday
    commands        - Returns the list of commands associated with this bot


# Commands related to listing words. 

## `blacklist` and `rmblacklist`

The `blacklist` command is used to blacklist a given sentence so that the bot picks up those and uses them while applying the blacklist filter. The `rmblacklist` is used to remove a blacklisted sentence. 
As the blacklist filter has a high value, only words which might strongly imply a Non-Answer must be blacklisted. 
 
## `whitelist` and `rmwhitelist`

The `whitelist` command is similar to the `blacklist` command. The bot uses it while applying the whitelist filter. The `rmwhitelist` is used to remove a whitelist. 

## `addsalute`

The `addsalute` command is used to add a salutation which is normally used to end Non-answers. The salutations filter checks if the listed word is present in the last 3 lines. 


# Commands related to notifications

## `opt-in` command format

The command to `opt-in` for a tag needs three arguments. They are the tag, the type of the post and whether to notify when the user is inside the room or not. The third argument defaults to notifying the user only when they are inside room. 

E.g: 

 - `@Natty opt-in [python] all` : This notifies the user of all the posts identified by the bot tagged python, only when they are present in the room.
 - `@Natty opt-in [python] naa` : This notifies the user of all the posts that are identified as NAA by the bot and tagged python, only when they are present in the room. 
 - `@Natty opt-in [python] all always` : This notifies the user of all the posts identified by the bot tagged python, whether the user is present in the room, or not. 
 - `@Natty opt-in [python] naa always` : This notifies the user of all the posts that are identified as NAA by the bot and tagged python, whether the user is present in the room, or not. 
 - `@Natty opt-in all naa` : This notifies the user of all the posts that are identified as NAA by the bot only when they are present in the room. 
 - `@Natty opt-in all all always` : This notifies the user of all the posts identified by the bot, whether the user is present in the room, or not. 

## `opt-out` command format 

The `opt-out` command unlists the users only when the arguments are the same as those provided when they opted in. Using `opt-out everything` opts out an user from all notifications.

# Commands related to feedback

## `fetch` command format

The fetch command also takes a few arguments that can be used to get the posts that have not received feedback. The command alone, without any arguments returns a list of answerIds. The other options are 

 - `links` : This returns the answerIds of the posts along with the links to those posts. This is useful to send bulk feedback. Adding a number argument, returns the requested amount only. For example, to fetch 10 reports, we can use `@Natty fetch links 10`.
 - `deleted` : This returns the answerIds of the posts which are deleted. 
 - `amount` : This returns the number of available posts in the feedback queue. 
 - `Sentinel` : This returns the Sentinel links of the posts.
 - `FMS` : This returns the FMS links of the post. 

## `send` command format

The `send` command can be used to send mass feedback. However the feedback must be in the order of what is present in the feedback queue. The argument `reverse` can be used to send the feedback in the bottoms-up manner.
 
## `feedback` command format

The `feedback` command can be used to send feedback to an individual report.  The format of the command is `feedback postlink feedbackType`. 
The `feedback` command can be used to correct an incorrectly passed feedback, by re-sending the correct feedback. (The feedback can be monitored on [Sentinel](/Natty/sentinel))



# Easter egg commands 

Few commands like

 - `addcheckuser`
 - `hi`
 - `halp`
 - `wishBD`
 
are just small commands to keep the room in a lively mood. 

----

<sub>Back to [Home](/Natty)</sub>