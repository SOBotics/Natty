package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Say implements SpecialCommand {

    private Message message;

    public Say(Message message) {
        this.message = message;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"say");
    }

    @Override
    public void execute(Room room) {
        room.send(CommandUtils.extractData(message.getPlainContent()));
    }

    @Override
    public String description() {
        return "Echoes the user input";
    }

    @Override
    public String name() {
        return "say";
    }
}
