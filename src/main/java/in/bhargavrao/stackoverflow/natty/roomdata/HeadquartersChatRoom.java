package in.bhargavrao.stackoverflow.natty.roomdata;

import in.bhargavrao.stackoverflow.natty.commandlists.HeadquartersCommandsList;
import in.bhargavrao.stackoverflow.natty.printers.HeadquartersPostPrinter;
import in.bhargavrao.stackoverflow.natty.printers.PostPrinter;
import in.bhargavrao.stackoverflow.natty.services.RunnerService;
import in.bhargavrao.stackoverflow.natty.utils.enums.Site;
import in.bhargavrao.stackoverflow.natty.validators.AllowAllNewAnswersValidator;
import in.bhargavrao.stackoverflow.natty.validators.Validator;
import org.sobotics.chatexchange.chat.ChatHost;
import org.sobotics.chatexchange.chat.Room;
import org.sobotics.chatexchange.chat.event.MessageReplyEvent;
import org.sobotics.chatexchange.chat.event.UserMentionedEvent;

import java.util.Arrays;
import java.util.List;
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
        return event->new HeadquartersCommandsList().mention(room, event,service, getSiteName(), getSiteUrl(), true);
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

    @Override
    public ChatHost getHost() {
        return ChatHost.STACK_OVERFLOW;
    }

    @Override
    public List<Site> getSiteList() {
        return Arrays.asList(Site.STACK_OVERFLOW);
    }

    @Override
    public String getSiteName() {
        return "stackoverflow";
    }

    @Override
    public String getSiteUrl() {
        return "stackoverflow.com";
    }

}
