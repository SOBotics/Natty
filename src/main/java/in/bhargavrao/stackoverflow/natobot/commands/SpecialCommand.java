package in.bhargavrao.stackoverflow.natobot.commands;

import fr.tunaki.stackoverflow.chat.Room;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public interface SpecialCommand {
    public boolean validate();
    public void execute(Room room);
    public String description();
    public String name();
}
