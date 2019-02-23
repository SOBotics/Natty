package in.bhargavrao.stackoverflow.natty.commands;

import in.bhargavrao.stackoverflow.natty.exceptions.UnAuthorizedException;
import org.sobotics.chatexchange.chat.Room;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public interface Command {
    public boolean validate() throws UnAuthorizedException;
    public void execute(Room room);
    public String description();
    public String getName();
}
