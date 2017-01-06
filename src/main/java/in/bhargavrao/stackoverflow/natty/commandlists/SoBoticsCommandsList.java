package in.bhargavrao.stackoverflow.natty.commandlists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natty.commands.AddCheckUser;
import in.bhargavrao.stackoverflow.natty.commands.AddSalute;
import in.bhargavrao.stackoverflow.natty.commands.Alive;
import in.bhargavrao.stackoverflow.natty.commands.Blacklist;
import in.bhargavrao.stackoverflow.natty.commands.Check;
import in.bhargavrao.stackoverflow.natty.commands.Commands;
import in.bhargavrao.stackoverflow.natty.commands.Feedback;
import in.bhargavrao.stackoverflow.natty.commands.Fetch;
import in.bhargavrao.stackoverflow.natty.commands.Halp;
import in.bhargavrao.stackoverflow.natty.commands.Help;
import in.bhargavrao.stackoverflow.natty.commands.Hi;
import in.bhargavrao.stackoverflow.natty.commands.IsBlacklisted;
import in.bhargavrao.stackoverflow.natty.commands.IsWhitelisted;
import in.bhargavrao.stackoverflow.natty.commands.OptIn;
import in.bhargavrao.stackoverflow.natty.commands.OptOut;
import in.bhargavrao.stackoverflow.natty.commands.Quota;
import in.bhargavrao.stackoverflow.natty.commands.Remind;
import in.bhargavrao.stackoverflow.natty.commands.RemoveBlackList;
import in.bhargavrao.stackoverflow.natty.commands.RemoveRequest;
import in.bhargavrao.stackoverflow.natty.commands.RemoveWhitelist;
import in.bhargavrao.stackoverflow.natty.commands.Report;
import in.bhargavrao.stackoverflow.natty.commands.Say;
import in.bhargavrao.stackoverflow.natty.commands.Send;
import in.bhargavrao.stackoverflow.natty.commands.ShowRequests;
import in.bhargavrao.stackoverflow.natty.commands.SpecialCommand;
import in.bhargavrao.stackoverflow.natty.commands.Status;
import in.bhargavrao.stackoverflow.natty.commands.Whitelist;
import in.bhargavrao.stackoverflow.natty.commands.WishBirthday;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;
import in.bhargavrao.stackoverflow.natty.validators.AllowAllNewAnswersValidator;

/**
 * Created by bhargav.h on 28-Oct-16.
 */
public class SoBoticsCommandsList {

	private static final Logger LOGGER = LoggerFactory.getLogger(SoBoticsCommandsList.class);

    public void mention(Room room, PingMessageEvent event, boolean isReply){

        if(CheckUtils.checkIfUserIsBlacklisted(event.getUserId()))
            return;

        Message message = event.getMessage();
        List<SpecialCommand> commands = new ArrayList<>(Arrays.asList(
            new AddCheckUser(message),
            new AddSalute(message),
            new Alive(message),
            new Blacklist(message),
            new Check(message),
            new Feedback(message),
            new Fetch(message),
            new Halp(message),
            new Help(message),
            new Hi(message, event.getUserId()),
            new IsBlacklisted(message),
            new IsWhitelisted(message),
            new OptIn(message),
            new OptOut(message),
            new Quota(message),
            new Remind(message),
            new RemoveBlackList(message),
            new RemoveRequest(message),
            new RemoveWhitelist(message),
            new Report(message, new AllowAllNewAnswersValidator()),
            new Say(message),
            new Send(message),
            new ShowRequests(message),
            new Status(message),
            new Whitelist(message),
            new WishBirthday(message)
        ));

        commands.add(new Commands(message,commands));
        LOGGER.debug("Looking for the command to execute");

        for(SpecialCommand command: commands){
            if(command.validate()){
                command.execute(room);
            }
        }
        
        LOGGER.info(event.getMessage().getContent());
    }
}
