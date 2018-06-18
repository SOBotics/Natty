package in.bhargavrao.stackoverflow.natty.commandlists;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natty.commands.*;
import in.bhargavrao.stackoverflow.natty.services.RunnerService;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;
import in.bhargavrao.stackoverflow.natty.validators.AllowAllAnswersValidator;
import in.bhargavrao.stackoverflow.natty.validators.AllowAllNewAnswersValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bhargav.h on 01-Apr-17.
 */
public class TemporaryRoomCommandsList {
    public void mention(Room room, PingMessageEvent event, RunnerService service, String sitename, String siteurl, boolean isReply){
        if(CheckUtils.checkIfUserIsBlacklisted(event.getUserId()))
            return;
        Message message = event.getMessage();
        List<SpecialCommand> commands = new ArrayList<>(Arrays.asList(
            new Alive(message),
            new Help(message),
            new Check(message, sitename, siteurl),
            new Feedback(message, new AllowAllAnswersValidator(), 3.0,  sitename, siteurl),
            new Fetch(message, sitename, siteurl),
            new OptIn(message),
            new OptOut(message),
            new Send(message, new AllowAllAnswersValidator(), 3.0, sitename, siteurl),
            new Report(message, new AllowAllAnswersValidator(), 3.0, sitename, siteurl)
        ));
        commands.add(new Commands(message,commands));
        for(SpecialCommand command: commands){
            if(command.validate()){
                command.execute(room);
            }
        }
        System.out.println(event.getMessage().getContent());
    }
}
