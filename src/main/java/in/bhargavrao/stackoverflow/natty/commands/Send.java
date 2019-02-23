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

import java.util.Collections;
import java.util.List;

/**
 * Created by bhargav.h on 23-Oct-16.
 */
public class Send extends  NormalCommand implements Command {

    private Message message;
    private Validator validator;
    private Double naaLimit;
    private String sitename;
    private String siteurl;

    public Send(Message message, Validator validator, Double naaLimit, String sitename, String siteurl) {
        super(message, "send");
        this.message = message;
        this.validator = validator;
        this.naaLimit = naaLimit;
        this.sitename = sitename;
        this.siteurl = siteurl;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"send") || CommandUtils.checkForCommand(message.getPlainContent(),"rsend") ;
    }

    @Override
    public void execute(Room room) {
        String data = CommandUtils.extractData(message.getPlainContent()).trim();
        String feedbacks[] = data.split(" ");
        StorageService service = new FileStorageService();

        List<String> lines = service.getReports(sitename);


        if(feedbacks.length==0 || (feedbacks.length==1 && feedbacks[0].equals("reverse"))){
            room.replyTo(message.getId(), "InputMismatchError, The code has been made Tuna Proof™");
            return;
        }
        if(message.getPlainContent().split(" ")[1].toLowerCase().equals("rsend")){
            Collections.reverse(lines);
        }
        if(feedbacks.length>lines.size()){
            room.replyTo(message.getId(), feedbacks.length+" feedbacks, "+lines.size()+" reports");
            return;
        }
        for(int i = 0 ;i<feedbacks.length;i++){
            String feedback = feedbacks[i].toLowerCase();
            String line = lines.get(i);
            if(feedback.equals("t")) feedback = "tp";
            if(feedback.equals("f")) feedback = "fp";
            if(feedback.equals("n")) feedback = "ne";

            if (!feedback.equals("tp") && service.checkAutoFlag(Long.parseLong(line),sitename)){
                room.send("False positive feedback on Autoflag, please retract @Bhargav or @Petter");
            }
            if(feedback.equals("ne")||feedback.equals("tp")||feedback.equals("fp")) {
                try {
                    new FeedbackHandlerService(sitename, siteurl).handleFeedback(message.getUser(), feedback, line);
                } catch (FeedbackInvalidatedException e) {
                    room.send(e.getMessage());
                } catch (PostNotStoredException e) {
                    if (feedback.equals("tp")) {
                        User user = message.getUser();
                        room.send(new ReportHandlerService(sitename, siteurl, validator, naaLimit, user).reportPost(line));
                    }
                }
            }
        }

    }

    @Override
    public String description() {
        return "Sends a mass feedback, see the wiki for more details";
    }

}
