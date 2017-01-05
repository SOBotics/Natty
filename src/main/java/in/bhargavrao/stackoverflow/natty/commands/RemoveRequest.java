package in.bhargavrao.stackoverflow.natty.commands;

import java.io.IOException;
import java.util.Arrays;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natty.utils.FileUtils;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class RemoveRequest implements SpecialCommand {

    private Message message;

    public RemoveRequest(Message message) {
        this.message = message;
    }

    @Override
    public boolean validate() {

        return CommandUtils.checkForCommand(message.getPlainContent(),"rmreq");
    }

    @Override
    public void execute(Room room) {

        String filename = FilePathUtils.featureRequests;
        String data = CommandUtils.extractData(message.getPlainContent()).trim();
        try{

            int linenumbers[] = Arrays.stream(data.split(" ")).mapToInt(Integer::parseInt).toArray();
            FileUtils.removeFromFileLines(filename,linenumbers);
            room.replyTo(message.getId(), "Done");
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
