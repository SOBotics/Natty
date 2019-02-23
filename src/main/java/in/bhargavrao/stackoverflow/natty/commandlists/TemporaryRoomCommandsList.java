package in.bhargavrao.stackoverflow.natty.commandlists;

import in.bhargavrao.stackoverflow.natty.commands.*;
import in.bhargavrao.stackoverflow.natty.services.RunnerService;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;
import in.bhargavrao.stackoverflow.natty.validators.AllowAllAnswersValidator;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;
import org.sobotics.chatexchange.chat.event.PingMessageEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bhargav.h on 01-Apr-17.
 */
public class TemporaryRoomCommandsList extends CommandsList {
    public void mention(Room room, PingMessageEvent event, RunnerService service, String sitename, String siteurl, boolean isReply){
        if(CheckUtils.checkIfUserIsBlacklisted(event.getUserId()))
            return;
        Message message = event.getMessage();
        AllowAllAnswersValidator validator = new AllowAllAnswersValidator();
        List<Command> commands = new ArrayList<>(Arrays.asList(
            new Alive(message),
            new Help(message),
            new Check(message, sitename, siteurl),
            new Feedback(message, validator, 3.0,  sitename, siteurl),
            new Fetch(message, sitename, siteurl),
            new OptIn(message),
            new OptOut(message),
            new Send(message, validator, 3.0, sitename, siteurl),
            new Reboot(message, service),
            new Report(message, validator, 3.0, sitename, siteurl)
        ));
        commands.add(new Commands(message,commands));

        LOGGER.debug("Looking for the command to execute");
        executeCommand(room, commands);
        LOGGER.info(event.getMessage().getPlainContent());
    }
}
