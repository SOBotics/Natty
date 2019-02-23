package in.bhargavrao.stackoverflow.natty.commands;

import in.bhargavrao.stackoverflow.natty.services.RunnerService;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

/**
 * Created by bhargav.h on 27-Jan-17.
 */
public class Reboot extends ReservedCommand implements Command {

    private Message message;
    private RunnerService service;

    public Reboot(Message message, RunnerService service) {
        super(message, "reboot");
        this.message = message;
        this.service = service;
    }

    @Override
    public void execute(Room room) {
        room.replyTo(message.getId(),"Rebooting, please standby");
        service.reboot();
    }

    @Override
    public String description() {
        return "Reboots the bot";
    }


}
