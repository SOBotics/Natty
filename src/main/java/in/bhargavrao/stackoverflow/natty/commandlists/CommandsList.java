package in.bhargavrao.stackoverflow.natty.commandlists;

import in.bhargavrao.stackoverflow.natty.commands.Command;
import in.bhargavrao.stackoverflow.natty.exceptions.UnAuthorizedException;
import in.bhargavrao.stackoverflow.natty.services.RunnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sobotics.chatexchange.chat.Room;
import org.sobotics.chatexchange.chat.event.PingMessageEvent;

import java.util.List;

public abstract class CommandsList {

    protected static final Logger LOGGER = LoggerFactory.getLogger(CommandsList.class);

    public abstract void mention(Room room, PingMessageEvent event, RunnerService service, String sitename,
                                 String siteurl, boolean isReply);

    public void executeCommand(Room room, List<Command> commands){
        try {
            for (Command command : commands) {
                if (command.validate() && command.authenticate()) {
                    command.execute(room);
                }
            }
        }
        catch (UnAuthorizedException e) {
            room.send("That is a moderator only command");
        }
    }
}
