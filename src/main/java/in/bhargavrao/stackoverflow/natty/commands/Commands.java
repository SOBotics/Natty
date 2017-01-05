package in.bhargavrao.stackoverflow.natty.commands;

import java.util.List;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.PrintUtils;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Commands implements SpecialCommand {

    private Message message;
    private List<SpecialCommand> commands;

    public Commands(Message message, List<SpecialCommand> commands) {
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
        for (SpecialCommand command: commands){
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
