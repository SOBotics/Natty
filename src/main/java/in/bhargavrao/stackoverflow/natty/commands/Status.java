package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.StatusUtils;

import java.time.Instant;
import java.util.List;

/**
 * Created by bhargav.h on 16-Oct-16.
 */
public class Status implements SpecialCommand {

    private Message message;
    private String sitename;
    private String siteurl;


    public Status(Message message, String sitename, String siteurl) {
        this.message = message;
        this.sitename = sitename;
        this.siteurl = siteurl;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"status");
    }

    @Override
    public void execute(Room room) {

        Instant startupDate = StatusUtils.startupDate;

        StorageService service = new FileStorageService();

        String statusMessage = "The bot is running from "+startupDate+". ";

        List<String> reports = service.getReports(sitename);

        if(reports != null && reports.size()!=0) {
            statusMessage += "There are " + reports.size() + " unconfirmed reports. ";
        }
        else {
            statusMessage += "All the reports have been tended to. ";
        }



        room.send(statusMessage);
    }

    @Override
    public String description() {
        return "Returns the status of the bot";
    }

    @Override
    public String name() {
        return "status";
    }
}
