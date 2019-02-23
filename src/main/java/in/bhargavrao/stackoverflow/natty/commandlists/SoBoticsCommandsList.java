package in.bhargavrao.stackoverflow.natty.commandlists;

import in.bhargavrao.stackoverflow.natty.commands.Command;
import in.bhargavrao.stackoverflow.natty.commands.hidden.AddCheckUser;
import in.bhargavrao.stackoverflow.natty.commands.hidden.Halp;
import in.bhargavrao.stackoverflow.natty.commands.hidden.Hi;
import in.bhargavrao.stackoverflow.natty.commands.hidden.WishBirthday;
import in.bhargavrao.stackoverflow.natty.commands.others.*;
import in.bhargavrao.stackoverflow.natty.commands.reserved.*;
import in.bhargavrao.stackoverflow.natty.services.RunnerService;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;
import in.bhargavrao.stackoverflow.natty.validators.AllowAllNewAnswersValidator;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;
import org.sobotics.chatexchange.chat.event.PingMessageEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bhargav.h on 28-Oct-16.
 */
public class SoBoticsCommandsList extends CommandsList {


	@Override
    public void mention(Room room, PingMessageEvent event, RunnerService service, String sitename, String siteurl, boolean isReply){

        if(CheckUtils.checkIfUserIsBlacklisted(event.getUserId()))
            return;

        Message message = event.getMessage();

        AllowAllNewAnswersValidator validator = new AllowAllNewAnswersValidator();
        List<Command> commands = new ArrayList<>(Arrays.asList(
            new AddCheckUser(message),
            new AddSalute(message),
            new Alive(message),
            new Blacklist(message),
            new Check(message, sitename, siteurl),
            new Delete(message),
            new Feedback(message, validator, 3.5, sitename, siteurl),
            new Fetch(message, sitename, siteurl),
            new Halp(message),
            new Help(message),
            new Hi(message, event.getUserId()),
            new IsBlacklisted(message),
            new IsWhitelisted(message),
            new OptIn(message),
            new OptOut(message),
            new Quota(message),
            new Reboot(message,service),
            new Remind(message),
            new RemoveBlackList(message),
            new RemoveRequest(message),
            new RemoveWhitelist(message),
            new Report(message, validator, 3.5, sitename, siteurl),
            new Say(message),
            new Send(message, validator, 3.5, sitename, siteurl),
            new ShowRequests(message),
            new Status(message, sitename, siteurl),
            new StopAutoflagging(message),
            new Test(message),
            new Whitelist(message),
            new WishBirthday(message)
        ));

        commands.add(new Commands(message,commands));


        LOGGER.debug("Looking for the command to execute");
        executeCommand(room, commands);
        LOGGER.info(event.getMessage().getPlainContent());
    }
}
