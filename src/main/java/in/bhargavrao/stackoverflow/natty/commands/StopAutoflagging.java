package in.bhargavrao.stackoverflow.natty.commands;

import in.bhargavrao.stackoverflow.natty.utils.AutoFlagUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

/**
 * Created by bhargav.h on 11-Jul-18.
 */
public class StopAutoflagging extends ReservedCommand implements Command {

    private Message message;

    public StopAutoflagging(Message message) {
        super(message, "stopflagging");
        this.message = message;
    }

    @Override
    public void execute(Room room) {
        room.send("Stopping Autoflagging on command (cc @BhargavRao @PetterFriberg)");
        AutoFlagUtils.shouldAutoflag = false;
    }

    @Override
    public String description() {
        return "Stops auto flagging";
    }

}
