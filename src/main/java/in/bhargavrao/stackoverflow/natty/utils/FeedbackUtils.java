package in.bhargavrao.stackoverflow.natty.utils;

import in.bhargavrao.stackoverflow.natty.model.FeedbackType;

public class FeedbackUtils {
    public static FeedbackType getFeedbackTypeFromFeedback(String feedback){
        switch (feedback){
            case "t": return FeedbackType.TRUE_POSITIVE;
            case "tp": return FeedbackType.TRUE_POSITIVE;
            case "f": return FeedbackType.FALSE_POSITIVE;
            case "fp": return FeedbackType.FALSE_POSITIVE;
            case "n": return FeedbackType.NEEDS_EDITING;
            case "ne": return FeedbackType.NEEDS_EDITING;
            case "tn": return FeedbackType.TRUE_NEGATIVE;
        }
        return null;
    }
}
