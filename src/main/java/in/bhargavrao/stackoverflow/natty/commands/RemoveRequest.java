package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natty.utils.FileUtils;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class RemoveRequest implements SpecialCommand {


    private PingMessageEvent event;
    private String message;

    public RemoveRequest(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }

    @Override
    public boolean validate() {

        return CommandUtils.checkForCommand(message,"rmreq");
    }

    @Override
    public void execute(Room room) {

        String filename = FilePathUtils.featureRequests;
        String data = CommandUtils.extractData(message).trim();
        try{

            int linenumbers[] = Arrays.stream(data.split(" ")).mapToInt(Integer::parseInt).toArray();
            FileUtils.removeFromFileLines(filename,linenumbers);
            room.replyTo(event.getMessage().getId(), "Done");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public String description() {
        return "Removes the request from the list of requests";
    }

    @Override
    public String name() {
        return "rmreq";
    }
}
