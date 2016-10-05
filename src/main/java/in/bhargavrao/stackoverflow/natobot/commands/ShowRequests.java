package in.bhargavrao.stackoverflow.natobot.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.utils.ApiUtils;
import in.bhargavrao.stackoverflow.natobot.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natobot.utils.FileUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class ShowRequests implements SpecialCommand {


    private PingMessageEvent event;
    private String message;

    public ShowRequests(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }

    @Override
    public boolean validate() {

        return CommandUtils.checkForCommand(message,"showreqs");
    }

    @Override
    public void execute(Room room) {

        String filename = "./lib/FeatureRequests.txt";
        try{
            List<String> lines = FileUtils.readFile(filename);
            String requestString = "";
            int i=0;
            for (String line: lines){
                requestString+= "    "+(i+1)+". "+line.trim()+"\n";
                i++;
            }
            if(lines.size()==0)
                room.replyTo(event.getMessage().getId(), "There are no requirements currently ");
            else {
                room.replyTo(event.getMessage().getId(), "The list of requests stored  ");
                room.send(requestString);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }
}
