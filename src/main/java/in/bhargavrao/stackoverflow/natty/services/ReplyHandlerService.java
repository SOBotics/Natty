package in.bhargavrao.stackoverflow.natty.services;

import in.bhargavrao.stackoverflow.natty.commands.Command;
import in.bhargavrao.stackoverflow.natty.commands.replies.*;
import in.bhargavrao.stackoverflow.natty.exceptions.UnAuthorizedException;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;
import org.sobotics.chatexchange.chat.event.PingMessageEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReplyHandlerService {

    public void reply(Room room, PingMessageEvent event, String sitename, String siteurl, boolean isReply) {


        if(CheckUtils.checkIfUserIsBlacklisted(event.getUserId()))
            return;

        Message message = event.getMessage();
        List<Command> commands = new ArrayList<>(Arrays.asList(
                new Autoflagged(message, event, sitename, siteurl),
                new Delete(message, event),
                new FalsePositive(message, event, sitename, siteurl),
                new NeedsEdit(message, event, sitename, siteurl),
                new TruePositive(message, event, sitename, siteurl),
                new Where(message),
                new Who(message, event, sitename, siteurl),
                new Why(message, event, sitename, siteurl)
        ));


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
