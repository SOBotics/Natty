package in.bhargavrao.stackoverflow.natty.roomdata;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.MessageReplyEvent;
import fr.tunaki.stackoverflow.chat.event.UserMentionedEvent;
import in.bhargavrao.stackoverflow.natty.commandlists.HeadquartersCommandsList;
import in.bhargavrao.stackoverflow.natty.printers.HeadquartersPostPrinter;
import in.bhargavrao.stackoverflow.natty.printers.PostPrinter;
import in.bhargavrao.stackoverflow.natty.services.RunnerService;
import in.bhargavrao.stackoverflow.natty.validators.AllowAllNewAnswersValidator;
import in.bhargavrao.stackoverflow.natty.validators.Validator;

import java.util.function.Consumer;

/**
 * Created by bhargav.h on 28-Dec-16.
 */
public class HeadquartersChatRoom implements BotRoom{
    @Override
    public int getRoomId() {
        return 126814;
    }

    @Override
    public Consumer<UserMentionedEvent> getMention(Room room, RunnerService service) {
        return event->new HeadquartersCommandsList().mention(room, event,service, true);
    }

    @Override
    public Consumer<MessageReplyEvent> getReply(Room room) {
        return null;
    }

    @Override
    public Validator getValidator() {
        return new AllowAllNewAnswersValidator();
    }

    @Override
    public double getNaaValue() {
        return 0;
    }

    @Override
    public PostPrinter getPostPrinter() {
        return new HeadquartersPostPrinter();
    }

    @Override
    public boolean getIsLogged() {
        return false;
    }


}
