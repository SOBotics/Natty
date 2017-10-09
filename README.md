
[![Build Status](https://travis-ci.org/SOBotics/Natty.svg?branch=master)](https://travis-ci.org/SOBotics/Natty)

[Natty](http://stackapps.com/questions/7049/natty-bringing-10k-moderation-to-all) 
---

# General

**Background and History**

The [New Answers to Old Questions](http://stackoverflow.com/tools/new-answers-old-questions) tool helps us to find all the answers that have been added to questions over 30 days old. However, the issue with the tool is that it is not real time. There are requests on [Stack Overflow Meta](http://meta.stackoverflow.com) to not only make it [real time](http://meta.stackoverflow.com/questions/312246/make-the-new-answers-to-old-questions-real-time) but also to [enhance the tool](http://meta.stackoverflow.com/questions/319952/enhance-the-new-answers-to-old-questions-moderator-tool) to add more features to it. However, these requests have not yet been implemented. 

**Why do we need the bot?**

This project aims to not only overcome the *"not real time"* issue but also to help those users below 10,000 reputation to take a look and help in moderating the new answers.

# Implementation

The bot queries the [Stack Exchange API](https://api.stackexchange.com/docs/answers) once every 30 seconds to get the list of the latest answers. The `question_id` from each of the answer is taken and the [Stack Exchange API](https://api.stackexchange.com/docs/questions-by-ids) is again queried for the list of posts with those IDs. Every answer that is posted after 30 days of the question is considered as a new answer to a old question and is returned by the bot. 

**False Positives and Filtering** 

As the bot returns all the posts, there are a lot of false positives and hence many filters are applied.

*High Filtering Rate - Return a lot of True Positives* 

 - **Reputation Filter**: The reputation filter is set at 50, which is the reputation that is needed to comment. Hence all the answers that are posted due to the lack of reputation are caught here.
 - **The last punctuation mark**: Whenever an answer ends with a question mark, the likelihood of the answer being open-ended/another question is very high. So in most cases, answers that are caught by this filter need to be 
    1. Edited to make it an answer that does not request clarification from the asker
    2. Flagged as NAA
 - **Salutations**: Those answers that end with "Thanks", etc, are most likely to be non-answers. Hence those answers that catch this filter are potential NAAs. 
 - **Single Line Only**: Great answers are seldom just a single line. A *single line* filter usually catches those posts that either need polishing or need to be flagged.
 - **Possible Link Only Answer**: The total length of the body is considered and the length of the link is considered. When the link is 60% longer than the total length, such an answer is detected as possibly link only. Those posts can then be flagged accordingly.

*Low Filtering Rate - Return less True Positives*

 - **Code Blocks**: Non Code Blocks usually imply that either the post is either
    1. Badly formatted: Here we can edit the post to improve the formatting.
    2. Not an Answer or a Very Low Quality Answer: Here we can flag them appropriately.
    Hence many answers that need either editing or flagging are caught by this filter. 
 - **Starting Keywords**: Interrogative pronouns like *What*, *Why*, *Who*, etc. There are many false positives with this, but the posts identified by this filter usually require editing. 
 - **Body Contains a question mark**: This filter returns those answers that contain a question mark that's not present either with a link or a code block. This again returns those posts that are potential NAAs. 

*Coupling Filters*

Coupling two or more filters together give a high rate of true positives. "The Single Line, Ends with ?" filter has nearly an accuracy of 100% while reporting NAAs. 

**Future Enhancements**

The future works about this include 
  - utilizing Natural Language Processing to discern if a post is a question and to report those. 
  - utilizing specific keywords (like, "Please Help", etc) to identify NAAs.

# Why are we focusing on old questions only?
   
  New Answers on Old Questions receive less attention than new answers on new questions. The period of 30 days was chosen as it was the same as the New Answers to Old Questions page in the 10k tools page. 

# Accounts 

  The Project is running in the [SOBotics](http://chat.stackoverflow.com/rooms/111347/sobotics) room. A sample image of a report is 
  
  ![Sample Image](http://i.stack.imgur.com/gyfzD.png)


 -----------------------
 
 More stuff to read - [Natty Docs](http://natty.sobotics.org)
