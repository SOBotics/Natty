package in.bhargavrao.stackoverflow.natty.exceptions;

import in.bhargavrao.stackoverflow.natty.model.Feedback;

/**
 * Created by bhargav.h on 22-Aug-17.
 */
public class FeedbackInvalidatedException extends Exception {

    private Feedback previousFeedback;
    private Feedback presentFeedback;

    public FeedbackInvalidatedException(){

    }

    public FeedbackInvalidatedException(Feedback a, Feedback b){
        presentFeedback = b;
        previousFeedback = a;
    }

    public Feedback getPreviousFeedback() {
        return previousFeedback;
    }

    public void setPreviousFeedback(Feedback previousFeedback) {
        this.previousFeedback = previousFeedback;
    }

    public Feedback getPresentFeedback() {
        return presentFeedback;
    }

    public void setPresentFeedback(Feedback presentFeedback) {
        this.presentFeedback = presentFeedback;
    }

    @Override
    public String getMessage() {
        if (presentFeedback!=null && previousFeedback!=null)
            return "Invalidated "+previousFeedback.getFeedbackType().toString()+ " from " + previousFeedback.getUsername();
        return "Invalidated the previous feedback";
    }
}
