package in.bhargavrao.stackoverflow.natty.services;

import in.bhargavrao.stackoverflow.natty.commands.Feedback;
import in.bhargavrao.stackoverflow.natty.model.ListType;
import in.bhargavrao.stackoverflow.natty.model.PostReport;

/**
 * Created by bhargav.h on 20-Aug-17.
 */
public interface StorageService {
    public void ListWord(String word, ListType type);
    public void UnListWord(String word, ListType type);
    public void SaveFeedback(Feedback feedback);
    public void StoreReport(PostReport report);
}
