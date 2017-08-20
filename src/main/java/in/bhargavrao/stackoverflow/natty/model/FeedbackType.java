package in.bhargavrao.stackoverflow.natty.model;

/**
 * Created by bhargav.h on 20-Aug-17.
 */
public enum FeedbackType {
    TRUE_POSITIVE {
        @Override
        public String toString(){ return "tp";}
    },
    FALSE_POSITIVE{
        @Override
        public String toString(){ return "fp";}
    },
    TRUE_NEGATIVE{
        @Override
        public String toString(){ return "tn";}
    },
    NEEDS_EDITING{
        @Override
        public String toString(){ return "ne";}
    }
}
