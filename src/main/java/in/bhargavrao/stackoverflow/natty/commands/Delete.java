package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.services.ApiService;
import in.bhargavrao.stackoverflow.natty.utils.*;

import java.io.IOException;

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
        String commentId = CommandUtils.extractData(message.getPlainContent()).trim();
        if(commentId.contains("/"))
            commentId = CommandUtils.getCommentId(commentId);
        if(CheckUtils.checkIfInteger(commentId)) {
            ApiService apiService = new ApiService("stackoverflow");
            try {
                apiService.deleteComment(Integer.parseInt(commentId));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
