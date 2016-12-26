package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class IsBlacklisted implements SpecialCommand {

    private PingMessageEvent event;
    private String message;

    public IsBlacklisted(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }
    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"isblacklisted");
    }

    @Override
    public void execute(Room room) {
        String word = CommandUtils.extractData(message);
        room.replyTo(event.getMessage().getId(), CheckUtils.checkIfBlackListed(word)?"The word is blacklisted":"The word is not blacklisted");
    }

    @Override
    public String description() {
        return "Checks if the given statement is blacklisted";
    }

    @Override
    public String name() {
        return "isblacklisted";
    }
}
