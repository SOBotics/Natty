package in.bhargavrao.stackoverflow.natty.services;

import in.bhargavrao.stackoverflow.natty.exceptions.NoSuchUserFoundException;
import in.bhargavrao.stackoverflow.natty.model.*;

import java.util.List;

/**
 * Created by bhargav.h on 20-Aug-17.
 */
public interface StorageService {

    String listWord(String word, ListType type);
    String unListWord(String word, ListType type);
    boolean checkListWord(String word, ListType type);
    List<String> getListWords(ListType type);


    String addCheckUsers(Integer userid, String message, String sitename);
    String checkUsers(Integer userid, String sitename) throws NoSuchUserFoundException;


    List<OptedInUser> getOptedInUsers(String tagname, long roomId);
    String addOptedInUser(OptedInUser user);
    String removeOptedInUser(OptedInUser user);
    String removeAllOptIn(long userid);


    String storeReminders(String reminder);
    List<String> retrieveReminders();
    String deleteReminder(int reminderIndex);
    String deleteReminders(int[] reminderIndexes);

    String storeReport(SavedReport report, String sitename);
    List<SavedReport> getFullReports(String sitename);
    List<String> getReports(String sitename);
    boolean checkIfReported(String postId, String sitename);
    String retrieveReport(String postId, String sitename);


    String saveFeedback(Feedback feedback, SavedReport report, String sitename);
    String addFeedback(Feedback feedback, SavedReport report, String sitename);
    String invalidateFeedback(Feedback feedback, SavedReport report, String sitename);
    FeedbackType getFeedback(String postId, String sitename);
    String retrieveFeedback(String postId, String sitename);
    String retrieveFeedbackUserLog(String postId, String sitename);

    String getSentinelId(String postId, String sitename);
    String storeSentinelData(long postId, long sentinelId, String sitename);

    String getIntelligentBlacklistJson();
    String addIntelligentBlacklistJson(String json);

    String addAutoFlag(long postId, String sitename);
    boolean checkAutoFlag(long postId, String sitename);
}
