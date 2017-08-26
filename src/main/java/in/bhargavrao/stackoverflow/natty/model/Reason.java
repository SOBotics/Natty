package in.bhargavrao.stackoverflow.natty.model;

/**
 * Created by bhargav.h on 20-Aug-17.
 */
public class Reason {

    private String reasonName;
    private String subReason;

    public Reason(String reasonName, String subReason) {
        this.reasonName = reasonName;
        this.subReason = subReason;
    }

    public Reason() {
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public String getSubReason() {
        return subReason;
    }

    public void setSubReason(String subReason) {
        this.subReason = subReason;
    }

    @Override
    public String toString() {
        return "Reason{" +
                "reasonName='" + reasonName + '\'' +
                ", subReason='" + subReason + '\'' +
                '}';
    }
}
