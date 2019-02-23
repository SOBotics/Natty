package in.bhargavrao.stackoverflow.natty.commands;

import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import in.bhargavrao.stackoverflow.natty.utils.StatusUtils;
import org.sobotics.chatexchange.chat.Message;
import org.sobotics.chatexchange.chat.Room;

import java.time.Instant;
import java.util.List;

/**
 * Created by bhargav.h on 16-Oct-16.
 */
public class Status extends NormalCommand implements Command {

    private Message message;
    private String sitename;
    private String siteurl;


    public Status(Message message, String sitename, String siteurl) {
        super(message, "status");
        this.message = message;
        this.sitename = sitename;
        this.siteurl = siteurl;
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

}
