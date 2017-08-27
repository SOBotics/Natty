package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.exceptions.FeedbackInvalidatedException;
import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.PostUtils;

/**
 * Created by bhargav.h on 29-Nov-16.
 */
public class Feedback implements SpecialCommand {

    private Message message;
    private String sitename;
    private String siteurl;

    public Feedback(Message message, String sitename, String siteurl) {
        this.message = message;
        this.sitename = sitename;
        this.siteurl = siteurl;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"feedback");
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
        if (type.equals("fp") && service.checkAutoFlag(Long.parseLong(word),sitename)){
            room.send("False positive feedback on Autoflag, please retract @Bhargav or @Petter");
        }

        if(type.equals("tp")||type.equals("fp")||type.equals("ne")||type.equals("t")||type.equals("f")||type.equals("n")) {
            try {
                PostUtils.handleFeedback(message.getUser(), type, word, sitename, siteurl);
            } catch (FeedbackInvalidatedException e) {
                room.send(e.getMessage());
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

    @Override
    public String name() {
        return "feedback";
    }
}
