package in.bhargavrao.stackoverflow.natobot.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natobot.utils.PrintUtils;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Help implements SpecialCommand {

    private PingMessageEvent event;
    private String message;

    public Help(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }


    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"help");
    }

    @Override
    public void execute(Room room) {
        room.replyTo(event.getMessage().getId(),PrintUtils.printHelp());
    }

    @Override
    public String description() {
        return "Returns information regarding the chatbot";
    }

    @Override
    public String name() {
        return "help";
    }
}
