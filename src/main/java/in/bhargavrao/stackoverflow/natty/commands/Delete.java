package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.entities.Natty;
import in.bhargavrao.stackoverflow.natty.entities.Post;
import in.bhargavrao.stackoverflow.natty.entities.PostReport;
import in.bhargavrao.stackoverflow.natty.entities.autocomments.AutoComment;
import in.bhargavrao.stackoverflow.natty.utils.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by bhargav.h on 22-Jan-17.
 */
public class Delete implements SpecialCommand {

    private Message message;

    public Delete(Message message) {
        this.message = message;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"delete");
    }

    @Override
    public void execute(Room room) {
        // TODO
    }

    @Override
    public String description() {
        return "Deletes a given comment";
    }

    @Override
    public String name() {
        return "delete";
    }
}
