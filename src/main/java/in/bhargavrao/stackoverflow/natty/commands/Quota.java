package in.bhargavrao.stackoverflow.natty.commands;

import in.bhargavrao.stackoverflow.natty.services.ApiService;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

import java.io.IOException;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Quota extends ReservedCommand implements Command {

    private Message message;

    public Quota(Message message) {
        super(message, "isblacklisted");
        this.message = message;
    }

    @Override
    public void execute(Room room) {
        ApiService apiService = new ApiService("stackoverflow");
        try {
            apiService.getAnswerDetailsById(1);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        room.replyTo(message.getId(), "The remaining quota is "+apiService.getQuota());
    }

    @Override
    public String description() {
        return "Returns the remaining API Quota";
    }

}
