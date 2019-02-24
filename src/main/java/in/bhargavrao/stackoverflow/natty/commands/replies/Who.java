package in.bhargavrao.stackoverflow.natty.commands.replies;

import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;
import org.sobotics.chatexchange.chat.event.PingMessageEvent;

import java.util.List;

/**
 * Created by bhargav.h on 22-Feb-19.
 */
public class Who extends ReplyCommand {

    private Message message;
    private PingMessageEvent event;
    private String sitename;
    private String siteurl;

    public Who(Message message, PingMessageEvent event, String sitename, String siteurl) {
        super(message, "who");
        this.message = message;
        this.event = event;
        this.sitename = sitename;
        this.siteurl = siteurl;
    }

    @Override
    public void execute(Room room) {

        String linkToPost = getAnswerIdFromMessage(room, event, siteurl);

        StorageService service = new FileStorageService();
        List<String> logs = service.retrieveFeedbackUserLogs(linkToPost, sitename);

        String reply="";
        for (String log:logs){
            String [] splits = log.split(",");
            reply+= splits[1]+" by "+splits[3]+"; ";
        }
        room.send(reply);
    }

    @Override
    public String description() {
        return "";
    }

}
