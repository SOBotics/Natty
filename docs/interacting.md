# Interacting with the bot

### Types of Feedback 

There are 3 types of feedback, which are 

1. ***`tp`*** : This means that the post reported by the bot is *"flaggable"*. If you flag the post which has been reported either as spam, offensive, NAA or VLQ, reply to that particular message with `tp` or `t`. 

2. ***`ne`*** : This means that the post reported by the bot is *badly formatted* and needs an edit. If you edit the post which has been reported, reply to that particular message with `ne` or `n`. 

3. ***`fp`*** : This means that the post reported by the bot neither needs to be flagged nor needs to be edited. If such is the case, then reply to that particular message with `fp` or `f`. 

**Important Notes**:

1. The feedback must be a reply to that message only. 
2. In few cases, The report spans over 2 messages. In those cases, reply to the first message. 
3. The feedback needs to start with `tp`, `fp` or `ne` as needed. There can be any other wordings after that. 

To check if your feedback has been received, check for the feedback on the [Sentinel](/Natty/sentinel) page of that particular post. 

### Mass Feedback 

Whenever there has been a long list of reports that have not been given feedback to, we can use the [`fetch`](https://github.com/SOBotics/NATOBot/wiki/Commands#fetch-command-format) and [`send`](https://github.com/SOBotics/NATOBot/wiki/Commands#send-command-format) commands to give mass feedback. 

The format for sending mass feedback is very easy. At first you need to request for a set of links. This can be done as follows: 

    @Natty fetch links 

To which the bot replies with a set of links. The feedback needs to be sent in order of the returned reports. This can be done as follows 

    @Natty send tp t fp unclear ne n f tp 

**Important Notes**: 

1. The mass feedback must be a mention and not a reply to the bot. 
2. If you need to skip a report, Send unclear or u. Note that those reports would be skipped and hence at the start of the next `send` batch, you need to again skip those. 
3. If you are unsure whether a report has been skipped or not, please use the `fetch` command again. It is always better to be sure than sorry. 

