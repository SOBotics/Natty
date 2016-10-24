package in.bhargavrao.stackoverflow.natobot.entities;

import java.util.List;

/**
 * Created by bhargav.h on 20-Oct-16.
 */
public class NatoReport {
    NatoPost post;
    double naaValue;
    List<String> caughtFor;
    List<Double> caughtForValues;

    public NatoReport(NatoPost post, double naaValue, List<String> caughtFor, List<Double> caughtForValues) {
        this.post = post;
        this.naaValue = naaValue;
        this.caughtFor = caughtFor;
        this.caughtForValues = caughtForValues;
    }

    public NatoPost getPost() {
        return post;
    }

    public void setPost(NatoPost post) {
        this.post = post;
    }

    public double getNaaValue() {
        return naaValue;
    }

    public void setNaaValue(double naaValue) {
        this.naaValue = naaValue;
    }

    public List<String> getCaughtFor() {
        return caughtFor;
    }

    public void setCaughtFor(List<String> caughtFor) {
        this.caughtFor = caughtFor;
    }

    public List<Double> getCaughtForValues() {
        return caughtForValues;
    }

    public void setCaughtForValues(List<Double> caughtForValues) {
        this.caughtForValues = caughtForValues;
    }
}
