package in.bhargavrao.stackoverflow.natobot.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.services.StatsService;
import in.bhargavrao.stackoverflow.natobot.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natobot.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natobot.utils.FileUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by bhargav.h on 16-Oct-16.
 */
public class Status implements SpecialCommand {


    private PingMessageEvent event;
    private String message;

    public Status(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"status");
    }

    @Override
    public void execute(Room room) {
        room.send(StatsService.getStatus());
    }
}
