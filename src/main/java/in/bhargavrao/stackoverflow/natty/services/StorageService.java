package in.bhargavrao.stackoverflow.natty.services;

import in.bhargavrao.stackoverflow.natty.commands.Feedback;
import in.bhargavrao.stackoverflow.natty.model.ListType;
import in.bhargavrao.stackoverflow.natty.model.PostReport;

/**
 * Created by bhargav.h on 20-Aug-17.
 */
public interface StorageService {
    public String ListWord(String word, ListType type);
    public String UnListWord(String word, ListType type);
    public String AddCheckUsers(Integer userid, String message);
    public String SaveFeedback(Feedback feedback);
    public String StoreReport(PostReport report);

}
