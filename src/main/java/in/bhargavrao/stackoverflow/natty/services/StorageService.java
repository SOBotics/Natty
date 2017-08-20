package in.bhargavrao.stackoverflow.natty.services;

import in.bhargavrao.stackoverflow.natty.commands.Feedback;
import in.bhargavrao.stackoverflow.natty.exceptions.NoSuchUserFoundException;
import in.bhargavrao.stackoverflow.natty.model.ListType;
import in.bhargavrao.stackoverflow.natty.model.OptedInUser;
import in.bhargavrao.stackoverflow.natty.model.PostReport;

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

    public String storeReminders(String reminder);
    public List<String> retrieveReminders();
    public String deleteReminder(int reminderIndex);
    public String deleteReminders(int[] reminderIndexes);

    public String saveFeedback(Feedback feedback);
    public String storeReport(PostReport report);

}
