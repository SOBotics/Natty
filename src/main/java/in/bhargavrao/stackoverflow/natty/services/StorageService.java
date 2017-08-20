package in.bhargavrao.stackoverflow.natty.services;

import in.bhargavrao.stackoverflow.natty.commands.Feedback;
import in.bhargavrao.stackoverflow.natty.exceptions.NoSuchUserFoundException;
import in.bhargavrao.stackoverflow.natty.model.ListType;
import in.bhargavrao.stackoverflow.natty.model.PostReport;

/**
 * Created by bhargav.h on 20-Aug-17.
 */
public interface StorageService {
    public String listWord(String word, ListType type);
    public String unListWord(String word, ListType type);
    public String addCheckUsers(Integer userid, String message, String sitename);
    public String checkUsers(Integer userid, String sitename) throws NoSuchUserFoundException;
    public String storeReminders(String reminder);
    public String saveFeedback(Feedback feedback);
    public String storeReport(PostReport report);

}
