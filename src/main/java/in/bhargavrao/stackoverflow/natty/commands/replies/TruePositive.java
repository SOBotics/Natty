package in.bhargavrao.stackoverflow.natty.commands.replies;

import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.event.PingMessageEvent;

/**
 * Created by bhargav.h on 22-Feb-19.
 */
public class TruePositive extends Feedback {

    private Message message;

    public TruePositive(Message message, PingMessageEvent event, String sitename, String siteurl) {
        super(message, "tp", "t", event, sitename, siteurl);
        this.message = message;
    }

    @Override
    public String description() {
        return "";
    }

}
