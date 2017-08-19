package in.bhargavrao.stackoverflow.natty.model;

/**
 * Created by bhargav.h on 20-Aug-17.
 */
public class Feedback {
    private String username;
    private Integer userId;
    private Integer feedbackId;
    private FeedbackType feedbackType;

    public Feedback() {
    }

    public Feedback(String username, Integer userId, Integer feedbackId, FeedbackType feedbackType) {
        this.username = username;
        this.userId = userId;
        this.feedbackId = feedbackId;
        this.feedbackType = feedbackType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
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
                ", feedbackId=" + feedbackId +
                ", feedbackType=" + feedbackType +
                '}';
    }
}
