package in.bhargavrao.stackoverflow.natobot.commandlists;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.commands.*;
import in.bhargavrao.stackoverflow.natobot.utils.CheckUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhargav.h on 28-Oct-16.
 */
public class SoBoticsCommandsList {
    public void mention(Room room, PingMessageEvent event, boolean isReply){

        if(CheckUtils.checkIfUserIsBlacklisted(event.getUserId()))
            return;

        List<SpecialCommand> commands = new ArrayList<SpecialCommand>(){{
            add(new AddCheckUser(event));
            add(new AddSalute(event));
            add(new Alive(event));
            add(new Blacklist(event));
            add(new Check(event));
            add(new Fetch(event));
            add(new Halp(event));
            add(new Help(event));
            add(new Hi(event));
            add(new IsBlacklisted(event));
            add(new IsWhitelisted(event));
            add(new OptIn(event));
            add(new OptOut(event));
            add(new Quota(event));
            add(new Remind(event));
            add(new RemoveBlackList(event));
            add(new RemoveRequest(event));
            add(new RemoveWhitelist(event));
            add(new Report(event));
            add(new Say(event));
            add(new Send(event));
            add(new ShowRequests(event));
            add(new Status(event));
            add(new Whitelist(event));
            add(new WishBirthday(event));
        }};

        commands.add(new Commands(event,commands));

        for(SpecialCommand command: commands){
            if(command.validate()){
                command.execute(room);
            }
        }
        System.out.println(event.getMessage().getContent());
    }
}
