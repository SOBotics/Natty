
[![Build Status](https://travis-ci.org/SOBotics/Natty.svg?branch=master)](https://travis-ci.org/SOBotics/Natty)

[Natty](http://stackapps.com/questions/7049/natty-bringing-10k-moderation-to-all) 
---

# General

**Background and History**

The [New Answers to Old Questions](http://stackoverflow.com/tools/new-answers-old-questions) tool helps us find all new answers to questions that are 30 days old. However, there's an issue with the tool: It's not real time. There are requests on [Stack Overflow Meta](http://meta.stackoverflow.com) to not only make it [real time](http://meta.stackoverflow.com/questions/312246/make-the-new-answers-to-old-questions-real-time), but there are also request to [enhance the tool](http://meta.stackoverflow.com/questions/319952/enhance-the-new-answers-to-old-questions-moderator-tool) to add more features to it. However, these requests have not yet been implemented. 

**Why do we need the bot?**

This project aims to not only overcome the issue of the tool not being real time, but also to allow users under 10,000 reputation to be able to take a look and help to moderate the new answers to old questions.


# Accounts 

  The Project is running in the [SOBotics](http://chat.stackoverflow.com/rooms/111347/sobotics) room. A sample image of a report is 
  
  ![Sample Image](http://i.stack.imgur.com/gyfzD.png)


# Setup

  You need to just clone the directory and run the setup file. 

    git clone https://github.com/SOBotics/Natty.git
    sh setup.sh
 
  It will ask you for a set of data and then create the jar which you can run.


 -----------------------
 
 You can read more (including the complete setup) at the [Natty Docs](http://natty.sobotics.org) on http://SOBotics.org.
