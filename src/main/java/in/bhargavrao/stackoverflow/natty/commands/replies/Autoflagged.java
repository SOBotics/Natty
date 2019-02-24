package in.bhargavrao.stackoverflow.natty.commands.replies;

import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;
import org.sobotics.chatexchange.chat.event.PingMessageEvent;

/**
 * Created by bhargav.h on 22-Feb-19.
 */
public class Autoflagged extends ReplyCommand {

    private Message message;
    private PingMessageEvent event;
    private String sitename;
    private String siteurl;

    public Autoflagged(Message message, PingMessageEvent event, String sitename, String siteurl) {
        super(message, "autoflagged");
        this.message = message;
        this.event = event;
        this.sitename = sitename;
        this.siteurl = siteurl;
    }


    @Override
    public void execute(Room room) {
        String linkToPost = getAnswerIdFromMessage(room, event, siteurl);
        StorageService service = new FileStorageService();
        boolean autoflagged = service.checkAutoFlag(Long.parseLong(linkToPost), sitename);
        room.send("The post was "+(autoflagged?"":"not ")+"autoflagged by Natty");
    }

    @Override
    public String description() {
        return "";
    }

}
