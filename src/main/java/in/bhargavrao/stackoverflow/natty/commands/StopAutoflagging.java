package in.bhargavrao.stackoverflow.natty.commands;

import in.bhargavrao.stackoverflow.natty.utils.AutoFlagUtils;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

/**
 * Created by bhargav.h on 11-Jul-18.
 */
public class StopAutoflagging implements SpecialCommand {

    private Message message;

    public StopAutoflagging(Message message) {
        this.message = message;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"stopflagging");
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

    @Override
    public String name() {
        return "stopflagging";
    }
}
