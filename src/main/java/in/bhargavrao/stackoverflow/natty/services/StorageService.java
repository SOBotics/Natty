package in.bhargavrao.stackoverflow.natty.services;

import in.bhargavrao.stackoverflow.natty.exceptions.NoSuchUserFoundException;
import in.bhargavrao.stackoverflow.natty.model.*;

import java.util.List;

/**
 * Created by bhargav.h on 20-Aug-17.
 */
public interface StorageService {


    public String listWord(String word, ListType type);
    public String unListWord(String word, ListType type);
    public boolean checkListWord(String word, ListType type);
    public List<String> getListWords(ListType type);


    public String addCheckUsers(Integer userid, String message, String sitename);
    public String checkUsers(Integer userid, String sitename) throws NoSuchUserFoundException;


    public List<OptedInUser> getOptedInUsers(String tagname, long roomId);
    public String addOptedInUser(OptedInUser user);
    public String removeOptedInUser(OptedInUser user);
    public String removeAllOptIn(long userid);


    public String storeReminders(String reminder);
    public List<String> retrieveReminders();
    public String deleteReminder(int reminderIndex);
    public String deleteReminders(int[] reminderIndexes);

    public String storeReport(SavedReport report, String sitename);
    public List<SavedReport> getFullReports(String sitename);
    public List<String> getReports(String sitename);
    public boolean checkIfReported(String postId, String sitename);
    public String retrieveReport(String postId, String sitename);


    public String saveFeedback(Feedback feedback, SavedReport report, String sitename);
    public String addFeedback(Feedback feedback, SavedReport report, String sitename);
    public String invalidateFeedback(Feedback feedback, SavedReport report, String sitename);
    public FeedbackType getFeedback(String postId, String sitename);
    public String retrieveFeedback(String postId, String sitename);
    public String retrieveFeedbackUserLog(String postId, String sitename);

    public String getSentinelId(String postId, String sitename);
    public String storeSentinelData(long postId, long sentinelId, String sitename);


    String addAutoFlag(long postId, String sitename);

    boolean checkAutoFlag(long postId, String sitename);
}
