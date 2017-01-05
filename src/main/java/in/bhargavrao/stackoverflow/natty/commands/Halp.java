package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;

/**
 * Created by bhargav.h on 27-Oct-16.
 */
public class Halp implements SpecialCommand {

    private Message message;

    public Halp(Message message) {
        this.message = message;
    }


    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"halp");
    }

    @Override
    public void execute(Room room) {
        room.replyTo(message.getId(),"Plop! You need to use help");
    }

    @Override
    public String description() {
        return "Plop, check it out";
    }

    @Override
    public String name() {
        return "halp";
    }
}
