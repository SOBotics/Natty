package in.bhargavrao.stackoverflow.natty.commands;

import org.sobotics.chatexchange.chat.Message;

public class HiddenCommand extends NormalCommand{


    public HiddenCommand(Message message, String commandName) {
        super(message, commandName);
    }

    public String description()  {
        return "";
    }
}
