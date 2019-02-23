package in.bhargavrao.stackoverflow.natty.commands.others;

import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Say extends NormalCommand {

    private Message message;

    public Say(Message message) {
        super(message, "say");
        this.message = message;
    }

    @Override
    public void execute(Room room) {
        if (message.getUser().isRoomOwner() || message.getUser().isModerator()) {
            room.send(CommandUtils.extractData(message.getPlainContent()));
        }
        else {
            room.send("@" + message.getUser().getName().replace(" ", "") + " wants to say - " +
            CommandUtils.extractData(message.getPlainContent()));
        }
    }

    @Override
    public String description() {
        return "Echoes the user input";
    }

}
