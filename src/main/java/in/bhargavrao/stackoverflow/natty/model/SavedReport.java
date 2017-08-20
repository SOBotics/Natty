package in.bhargavrao.stackoverflow.natty.model;

import java.time.Instant;
import java.util.List;

/**
 * Created by bhargav.h on 20-Aug-17.
 */
public class SavedReport {
    private Integer answerId;
    private Instant timestamp;
    private Double naaValue;
    private Integer bodyLength;
    private long reputation;
    private List<Reason> reasons;
    private FeedbackType feedbackType;

    public SavedReport(Integer answerId, Instant timestamp, Double naaValue, Integer bodyLength, long reputation, List<Reason> reasons, FeedbackType feedbackType) {
        this.answerId = answerId;
        this.timestamp = timestamp;
        this.naaValue = naaValue;
        this.bodyLength = bodyLength;
        this.reputation = reputation;
        this.reasons = reasons;
        this.feedbackType = feedbackType;
    }

    public SavedReport() {
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Double getNaaValue() {
        return naaValue;
    }

    public void setNaaValue(Double naaValue) {
        this.naaValue = naaValue;
    }

    public Integer getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(Integer bodyLength) {
        this.bodyLength = bodyLength;
    }

    public long getReputation() {
        return reputation;
    }

    public void setReputation(long reputation) {
        this.reputation = reputation;
    }

    public List<Reason> getReasons() {
        return reasons;
    }

    public void setReasons(List<Reason> reasons) {
        this.reasons = reasons;
    }

    public FeedbackType getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(FeedbackType feedbackType) {
        this.feedbackType = feedbackType;
    }

    @Override
    public String toString() {
        return "SavedReport{" +
                "answerId='" + answerId + '\'' +
                ", timestamp=" + timestamp +
                ", naaValue=" + naaValue +
                ", bodyLength=" + bodyLength +
                ", reputation=" + reputation +
                ", reasons=" + reasons +
                ", feedbackType=" + feedbackType +
                '}';
    }
}
