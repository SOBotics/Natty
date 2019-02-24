package in.bhargavrao.stackoverflow.natty.commands.replies;

import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.event.PingMessageEvent;

/**
 * Created by bhargav.h on 22-Feb-19.
 */
public class NeedsEdit extends Feedback {

    private Message message;

    public NeedsEdit(Message message, PingMessageEvent event, String sitename, String siteurl) {
        super(message, "ne", "n", event, sitename, siteurl);
        this.message = message;
    }

    @Override
    public String description() {
        return "";
    }

}
