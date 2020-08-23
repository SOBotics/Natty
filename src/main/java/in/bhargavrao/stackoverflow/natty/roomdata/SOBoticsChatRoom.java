package in.bhargavrao.stackoverflow.natty.roomdata;

import in.bhargavrao.stackoverflow.natty.commandlists.SoBoticsCommandsList;
import in.bhargavrao.stackoverflow.natty.printers.PostPrinter;
import in.bhargavrao.stackoverflow.natty.printers.SoBoticsPostPrinter;
import in.bhargavrao.stackoverflow.natty.services.ReplyHandlerService;
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
public class SOBoticsChatRoom implements BotRoom{
    @Override
    public int getRoomId() {
        return 111347;
    }

    @Override
    public Consumer<UserMentionedEvent> getMention(Room room, RunnerService service) {
        return event->new SoBoticsCommandsList().mention(room, event, service, getSiteName(), getSiteUrl(),  true);
    }

    @Override
    public Consumer<MessageReplyEvent> getReply(Room room) {
        return event-> new ReplyHandlerService().reply(room, event, getSiteName(), getSiteUrl(), true);
    }

    @Override
    public Validator getValidator() {
        return new AllowAllNewAnswersValidator();
    }

    @Override
    public double getNaaValue() {
        return 3.5;
    }

    @Override
    public PostPrinter getPostPrinter() {
        return new SoBoticsPostPrinter();
    }

    @Override
    public boolean getIsLogged() {
        return true;
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
