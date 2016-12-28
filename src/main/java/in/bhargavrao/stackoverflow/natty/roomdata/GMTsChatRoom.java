package in.bhargavrao.stackoverflow.natty.roomdata;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.MessageReplyEvent;
import fr.tunaki.stackoverflow.chat.event.UserMentionedEvent;
import in.bhargavrao.stackoverflow.natty.commandlists.GMTsCommandsList;
import in.bhargavrao.stackoverflow.natty.commandlists.RPublicCommandsList;
import in.bhargavrao.stackoverflow.natty.printers.GmtsPostPrinter;
import in.bhargavrao.stackoverflow.natty.printers.PostPrinter;
import in.bhargavrao.stackoverflow.natty.printers.RPublicPostPrinter;
import in.bhargavrao.stackoverflow.natty.utils.PostUtils;
import in.bhargavrao.stackoverflow.natty.validators.AllowOnlyTagValidator;
import in.bhargavrao.stackoverflow.natty.validators.RPublicValidator;
import in.bhargavrao.stackoverflow.natty.validators.Validator;

import java.util.function.Consumer;

/**
 * Created by bhargav.h on 28-Dec-16.
 */
public class GMTsChatRoom implements BotRoom{
    @Override
    public long getRoomId() {
        return 75819;
    }

    @Override
    public Consumer<UserMentionedEvent> getMention(Room room) {
        return event->new GMTsCommandsList().mention(room, event, true);
    }

    @Override
    public Consumer<MessageReplyEvent> getReply(Room room) {
        return event-> PostUtils.reply(room, event, true);
    }

    @Override
    public Validator getValidator() {
        return new AllowOnlyTagValidator("r");
    }

    @Override
    public double getNaaValue() {
        return 3.5;
    }

    @Override
    public PostPrinter getPostPrinter() {
        return new GmtsPostPrinter();
    }

    @Override
    public boolean getIsLogged() {
        return false;
    }


}
