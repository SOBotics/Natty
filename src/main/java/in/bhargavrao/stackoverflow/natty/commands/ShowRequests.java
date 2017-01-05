package in.bhargavrao.stackoverflow.natty.commands;

import java.io.IOException;
import java.util.List;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natty.utils.FileUtils;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class ShowRequests implements SpecialCommand {

    private Message message;

    public ShowRequests(Message message) {
        this.message = message;
    }

    @Override
    public boolean validate() {

        return CommandUtils.checkForCommand(message.getPlainContent(),"showreqs");
    }

    @Override
    public void execute(Room room) {

        String filename = FilePathUtils.featureRequests;
        try{
            List<String> lines = FileUtils.readFile(filename);
            String requestString = "";
            int i=0;
            for (String line: lines){
                requestString+= "    "+(i+1)+". "+line.trim()+"\n";
                i++;
            }
            if(lines.size()==0)
                room.replyTo(message.getId(), "There are no requirements currently ");
            else {
                room.replyTo(message.getId(), "The list of requests stored  ");
                room.send(requestString);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }

    @Override
    public String description() {
        return "Shows the list of requests";
    }

    @Override
    public String name() {
        return "showreqs";
    }
}
