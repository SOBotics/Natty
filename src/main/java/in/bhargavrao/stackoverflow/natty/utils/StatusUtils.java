package in.bhargavrao.stackoverflow.natty.utils;

import java.time.Instant;

/**
 * Provides statistics about the current status since launch
 * */
public class StatusUtils {
    public static Instant startupDate = null;
    
    /**
     * The time when the last check was finished
     * */
    public static Instant lastExecutionFinished = null;
    
    /**
     * The time when the last successful check was started
     * */
    public static Instant lastSucceededExecutionStarted = null;
}
