package in.bhargavrao.stackoverflow.natobot.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.utils.CheckUtils;
import in.bhargavrao.stackoverflow.natobot.utils.CommandUtils;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class IsWhitelisted implements SpecialCommand {

    private PingMessageEvent event;
    private String message;

    public IsWhitelisted(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getContent();
    }
    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"iswhitelisted");
    }

    @Override
    public void execute(Room room) {
        String word = CommandUtils.extractData(message);
        room.replyTo(event.getMessage().getId(), CheckUtils.checkIfWhiteListed(word)?"The word is whitelisted":"The word is not whitelisted");
    }
}
