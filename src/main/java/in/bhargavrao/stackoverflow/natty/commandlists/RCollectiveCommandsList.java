package in.bhargavrao.stackoverflow.natty.commandlists;

import in.bhargavrao.stackoverflow.natty.commands.Command;
import in.bhargavrao.stackoverflow.natty.commands.hidden.Hi;
import in.bhargavrao.stackoverflow.natty.commands.others.*;
import in.bhargavrao.stackoverflow.natty.services.RunnerService;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;
import org.sobotics.chatexchange.chat.event.PingMessageEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MDoubleDash on 28-Aug-23.
 */
public class RCollectiveCommandsList extends CommandsList {
    public void mention(Room room, PingMessageEvent event, RunnerService service, String sitename, String siteurl, boolean isReply){

        if(CheckUtils.checkIfUserIsBlacklisted(event.getUserId()))
            return;

        Message message = event.getMessage();
        List<Command> commands = new ArrayList<>(Arrays.asList(
            new Alive(message),
            new Check(message, sitename, siteurl),
            new Hi(message, event.getUserId()),
            new Help(message),
            new OptIn(message),
            new OptOut(message),
            new Status(message, sitename, siteurl)
        ));
        commands.add(new Commands(message,commands));

        LOGGER.debug("Looking for the command to execute");
        executeCommand(room, commands);
        LOGGER.info(event.getMessage().getPlainContent());
    }
}