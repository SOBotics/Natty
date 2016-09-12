package in.bhargavrao.stackoverflow.natobot.entities;

import com.google.gson.JsonObject;

import java.time.Instant;
import java.util.Arrays;

/**
 * Created by bhargav.h on 11-Sep-16.
 */
public class NatoPost {
    private String title;
    private String mainTag;
    private Instant answerCreationDate;
    private Instant questionCreationDate;
    private Integer answerID;
    private Integer questionID;
    private Integer reputation;
    private String body;
    private String[] tags;
    private Integer quota;
    private String userName;
    private Integer userID;
    private String bodyMarkdown;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getAnswerCreationDate() {
        return answerCreationDate;
    }

    public void setAnswerCreationDate(Instant answerCreationDate) {
        this.answerCreationDate = answerCreationDate;
    }

    public Instant getQuestionCreationDate() {
        return questionCreationDate;
    }

    public void setQuestionCreationDate(Instant questionCreationDate) {
        this.questionCreationDate = questionCreationDate;
    }

    public Integer getAnswerID() {
        return answerID;
    }

    public void setAnswerID(Integer answerID) {
        this.answerID = answerID;
    }

    public Integer getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
    }

    public Integer getReputation() {
        return reputation;
    }

    public void setReputation(Integer reputation) {
        this.reputation = reputation;
    }

    public String getMainTag() {
        return mainTag;
    }

    public void setMainTag(String mainTag) {
        this.mainTag = mainTag;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getBodyMarkdown() {
        return bodyMarkdown;
    }

    public void setBodyMarkdown(String bodyMarkdown) {
        this.bodyMarkdown = bodyMarkdown;
    }

    @Override
    public String toString() {
        return "NatoPost{" +
                "title='" + title + '\'' +
                ", mainTag='" + mainTag + '\'' +
                ", answerCreationDate=" + answerCreationDate +
                ", questionCreationDate=" + questionCreationDate +
                ", answerID= http://stackoverflow.com/a/" + answerID +
                ", questionID=" + questionID +
                ", reputation=" + reputation +
                ", tags=" + Arrays.toString(tags) +
                ", quota=" + quota +
                '}';
    }
}
