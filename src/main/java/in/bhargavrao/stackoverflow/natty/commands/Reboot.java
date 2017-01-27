package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.services.RunnerService;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.PrintUtils;

import java.util.List;

/**
 * Created by bhargav.h on 27-Jan-17.
 */
public class Reboot implements SpecialCommand {

    private Message message;
    private RunnerService service;

    public Reboot(Message message, RunnerService service) {
        this.message = message;
        this.service = service;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"reboot");
    }

    @Override
    public void execute(Room room) {
        room.replyTo(message.getId(),"Rebooting, please standby");
        service.reboot();
    }

    @Override
    public String name() {
        return "reboot";
    }

    @Override
    public String description() {
        return "Reboots the bot";
    }


}
