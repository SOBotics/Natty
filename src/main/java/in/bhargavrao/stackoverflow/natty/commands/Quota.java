package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natty.services.ApiService;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;

import java.io.IOException;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Quota implements SpecialCommand {


    private PingMessageEvent event;
    private String message;

    public Quota(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"quota");
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
        room.replyTo(event.getMessage().getId(), "The remaining quota is "+apiService.getQuota());
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
