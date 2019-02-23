package in.bhargavrao.stackoverflow.natty.commands.others;

import in.bhargavrao.stackoverflow.natty.exceptions.FeedbackInvalidatedException;
import in.bhargavrao.stackoverflow.natty.exceptions.PostNotStoredException;
import in.bhargavrao.stackoverflow.natty.model.FeedbackType;
import in.bhargavrao.stackoverflow.natty.services.FeedbackHandlerService;
import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.ReportHandlerService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.validators.Validator;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;
import org.sobotics.chatexchange.chat.User;

import java.util.List;

/**
 * Created by bhargav.h on 28-Oct-16.
 */
public class Report extends NormalCommand {

    private Message message;
    private Validator validator;
    private Double naaLimit;
    private String siteName;
    private String siteUrl;
    private FeedbackHandlerService feedbackHandlerService;

    public Report(Message message, Validator validator, Double naaLimit, String siteName, String siteUrl) {
        super(message, "report");
        this.message = message;
        this.validator = validator;
        this.naaLimit = naaLimit;
        this.siteName = siteName;
        this.siteUrl = siteUrl;
        this.feedbackHandlerService = new FeedbackHandlerService(siteName, siteUrl);
    }

    @Override
    public void execute(Room room) {
        try {

            String word = CommandUtils.extractData(message.getPlainContent()).trim();
            User user = message.getUser();
            if(word.contains("/"))
            {
                if (!word.contains(siteUrl)){
                    room.send("Post is not allowed to be reported in this room: Wrong Site");
                    return;
                }
                word = CommandUtils.getAnswerId(word);
            }

            StorageService service = new FileStorageService();

            if(service.checkIfReported(word, siteName)){
                room.replyTo(message.getId(), "Post already reported");
                feedbackHandlerService.handleFeedback(user, "tp", word);
            }
            else {

                FeedbackType feedback = service.getFeedback(word, siteName);

                if (feedback!=null) {
                    switch (feedback){
                        case TRUE_POSITIVE: room.replyTo(message.getId(), "Post already registered as True Positive"); break;
                        case FALSE_POSITIVE: room.replyTo(message.getId(), "Post already registered as False Positive"); break;
                        case NEEDS_EDITING: room.replyTo(message.getId(), "Post already registered as Needs Editing"); break;
                        case TRUE_NEGATIVE: room.replyTo(message.getId(), "Post already registered as True Negative"); break;
                    }
                    List<String> users = service.retrieveFeedbackUserLogs(word, siteName);
                    if(!users.contains(word+",tn,"+user.getId()+","+user.getName())) {
                        feedbackHandlerService.handleFeedback(user, "tp", word);
                    }
                }

                else {
                    room.send(new ReportHandlerService(siteName, siteUrl, validator, naaLimit, user).reportPost(word));
                }
            }
        } catch (FeedbackInvalidatedException | PostNotStoredException e) {
            e.printStackTrace();
            room.send(e.getMessage());
        }
    }

    @Override
    public String description() {
        return "Reports the mentioned post as a true negative NAA/VLQ";
    }

}
