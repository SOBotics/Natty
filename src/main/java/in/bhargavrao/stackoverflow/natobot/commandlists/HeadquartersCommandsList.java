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
public class HeadquartersCommandsList {
    public void mention(Room room, PingMessageEvent event, boolean isReply){

        if(CheckUtils.checkIfUserIsBlacklisted(event.getUserId()))
            return;

        List<SpecialCommand> commands = new ArrayList<SpecialCommand>(){{
            add(new Alive(event));
            add(new Help(event));
            add(new OptIn(event));
            add(new OptOut(event));
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
