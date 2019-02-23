package in.bhargavrao.stackoverflow.natty.commands;

import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.PrintUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

import java.util.List;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Commands implements Command {

    private Message message;
    private List<Command> commands;

    public Commands(Message message, List<Command> commands) {
        this.message = message;
        this.commands = commands;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"commands");
    }

    @Override
    public void execute(Room room) {
        room.replyTo(message.getId(),PrintUtils.printCommandHeader());
        String printstr = "";
        for (Command command: commands){
            printstr+="    "+padRight(command.name(),15)+" - "+command.description()+"\n";
        }

        room.send(printstr);
    }

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    @Override
    public String name() {
        return "commands";
    }

    @Override
    public String description() {
        return "Returns the list of commands associated with this bot";
    }


}
