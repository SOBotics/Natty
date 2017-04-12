package in.bhargavrao.stackoverflow.natty.roomdata;

import fr.tunaki.stackoverflow.chat.ChatHost;
import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.MessageReplyEvent;
import fr.tunaki.stackoverflow.chat.event.UserMentionedEvent;
import in.bhargavrao.stackoverflow.natty.printers.PostPrinter;
import in.bhargavrao.stackoverflow.natty.services.RunnerService;
import in.bhargavrao.stackoverflow.natty.validators.Validator;

import java.util.function.Consumer;

/**
 * Created by bhargav.h on 28-Dec-16.
 */
public interface BotRoom {
    public int getRoomId();
    public Consumer<UserMentionedEvent> getMention(Room room, RunnerService service);
    public Consumer<MessageReplyEvent> getReply(Room room);
    public Validator getValidator();
    public double getNaaValue();
    public PostPrinter getPostPrinter();
    public boolean getIsLogged();
    public ChatHost getHost();
    public String getSiteName();
    public String getSiteUrl();
}
