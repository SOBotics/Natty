package in.bhargavrao.stackoverflow.natty.model;

/**
 * Created by bhargav.h on 20-Aug-17.
 */
public class Feedback {
    private String username;
    private long userId;
    private FeedbackType feedbackType;

    public Feedback() {
    }

    public Feedback(String username, long userId, FeedbackType feedbackType) {
        this.username = username;
        this.userId = userId;
        this.feedbackType = feedbackType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public FeedbackType getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(FeedbackType feedbackType) {
        this.feedbackType = feedbackType;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "username='" + username + '\'' +
                ", userId=" + userId +
                ", feedbackType=" + feedbackType +
                '}';
    }
}
