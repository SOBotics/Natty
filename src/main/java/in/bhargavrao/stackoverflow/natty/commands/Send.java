package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.PostUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by bhargav.h on 23-Oct-16.
 */
public class Send implements SpecialCommand {

    private Message message;
    private String sitename;
    private String siteurl;

    public Send(Message message, String sitename, String siteurl) {
        this.message = message;
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
        for(int i  =0 ;i<feedbacks.length;i++){
            String feedback = feedbacks[i].toLowerCase();
            String line = lines.get(i);
            if(feedback.equals("t")) feedback = "tp";
            if(feedback.equals("f")) feedback = "fp";
            if(feedback.equals("n")) feedback = "ne";

            if(feedback.equals("ne")||feedback.equals("tp")||feedback.equals("fp")) {
                PostUtils.handleFeedback(message.getUser(), feedback, line, sitename, siteurl);
            }
        }

    }

    @Override
    public String description() {
        return "Sends a mass feedback, see the wiki for more details";
    }

    @Override
    public String name() {
        return "send";
    }
}
