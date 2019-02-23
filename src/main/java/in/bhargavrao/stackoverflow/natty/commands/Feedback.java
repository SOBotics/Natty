package in.bhargavrao.stackoverflow.natty.commands;

import in.bhargavrao.stackoverflow.natty.exceptions.FeedbackInvalidatedException;
import in.bhargavrao.stackoverflow.natty.exceptions.PostNotStoredException;
import in.bhargavrao.stackoverflow.natty.services.FeedbackHandlerService;
import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.ReportHandlerService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.validators.Validator;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;
import org.sobotics.chatexchange.chat.User;

/**
 * Created by bhargav.h on 29-Nov-16.
 */
public class Feedback extends NormalCommand implements Command {

    private Message message;
    private Validator validator;
    private Double naaLimit;
    private String sitename;
    private String siteurl;

    public Feedback(Message message, Validator validator, Double naaLimit, String sitename, String siteurl) {
        super(message, "feedback");
        this.message = message;
        this.validator = validator;
        this.naaLimit = naaLimit;
        this.sitename = sitename;
        this.siteurl = siteurl;
    }

    @Override
    public void execute(Room room) {
        String args[] = CommandUtils.extractData(message.getPlainContent()).trim().split(" ");

        if(args.length!=2){
            room.send("Error in arguments passed");
            return;
        }

        String word = args[0];
        String type = args[1];

        if(word.contains("/"))
        {
            word = CommandUtils.getAnswerId(word);
        }


        StorageService service = new FileStorageService();

        if (!type.equals("tp") && service.checkAutoFlag(Long.parseLong(word),sitename)){
            room.send("False positive feedback on Autoflag, please retract @Bhargav or @Petter");
        }

        if(type.equals("tp")||type.equals("fp")||type.equals("ne")||type.equals("t")||type.equals("f")||type.equals("n")) {
            try {
                new FeedbackHandlerService(sitename, siteurl).handleFeedback(message.getUser(), type, word);
            } catch (FeedbackInvalidatedException e) {
                room.send(e.getMessage());
            } catch (PostNotStoredException e) {
                if (type.equals("tp") || type.equals("t")) {
                    User user = message.getUser();
                    room.send("Post hasn't been reported earlier, manually reporting it now.");
                    room.send(new ReportHandlerService(sitename, siteurl, validator, naaLimit, user).reportPost(word));
                }
                else {
                    room.send("Post hasn't been reported earlier.");
                }
            }
        }
        else{
            room.send("Wrong feedback type");
        }
    }

    @Override
    public String description() {
        return "Provides feedback on a given post";
    }

}
