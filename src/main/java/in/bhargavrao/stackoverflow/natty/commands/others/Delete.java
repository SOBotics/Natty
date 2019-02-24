package in.bhargavrao.stackoverflow.natty.commands.others;

import in.bhargavrao.stackoverflow.natty.services.ApiService;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

import java.io.IOException;

/**
 * Created by bhargav.h on 22-Jan-17.
 */
public class Delete extends NormalCommand {

    private Message message;

    public Delete(Message message) {
        super(message, "delete");
        this.message = message;
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

}
