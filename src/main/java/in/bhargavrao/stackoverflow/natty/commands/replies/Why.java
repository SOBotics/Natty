package in.bhargavrao.stackoverflow.natty.commands.replies;

import in.bhargavrao.stackoverflow.natty.commands.others.Check;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;
import org.sobotics.chatexchange.chat.event.PingMessageEvent;

import java.io.IOException;

/**
 * Created by bhargav.h on 22-Feb-19.
 */
public class Why extends ReplyCommand {

    private Message message;
    private PingMessageEvent event;
    private String sitename;
    private String siteurl;

    public Why(Message message, PingMessageEvent event, String sitename, String siteurl) {
        super(message, "why");
        this.message = message;
        this.event = event;
        this.sitename = sitename;
        this.siteurl = siteurl;
    }


    @Override
    public void execute(Room room) {
        String linkToPost = getAnswerIdFromMessage(room, event, siteurl);

        try {
            String returnParams[] = new Check(message, sitename, siteurl).getCheckData(linkToPost, 2);
            room.replyTo(message.getId(), returnParams[0]);
            if (!returnParams[1].equals(""))
                room.send(returnParams[1]);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public String description() {
        return "";
    }

}
