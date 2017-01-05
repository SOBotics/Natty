package in.bhargavrao.stackoverflow.natty.commands;

import java.io.IOException;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.services.ApiService;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Quota implements SpecialCommand {

    private Message message;

    public Quota(Message message) {
        this.message = message;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"quota");
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

    @Override
    public String name() {
        return "quota";
    }
}
