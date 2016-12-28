package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natty.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Created by bhargav.h on 20-Oct-16.
 */
public class Fetch implements SpecialCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(Fetch.class);
    private PingMessageEvent event;
    private String message;

    public Fetch(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }

    @Override
    public boolean validate() {

        return CommandUtils.checkForCommand(message,"fetch");
    }

    @Override
    public String description() {
        return "Returns a list of posts that need feedback, See the wiki for more details";
    }

    @Override
    public void execute(Room room) {

        String data = CommandUtils.extractData(message).trim();
        String filename = FilePathUtils.outputReportLogFile;

        try{
            List<String> lines = FileUtils.readFile(filename);
            String returnString = String.join("; ", lines);

            if(data.split(" ")[0].equals("posts") && lines.size()<10 && lines.size()!=0){
                room.replyTo(event.getMessage().getId(), "Deprecated, Please use `fetch links` instead.");
            }
            else if(data.split(" ")[0].equals("links") && lines.size()!=0) {

                if (lines.size() == 0)
                    room.replyTo(event.getMessage().getId(), "All reports have been tended to");
                else {

                    int stopValue = lines.size();
                    if(CheckUtils.checkIfInteger(data.split(" ")[1])){
                        stopValue = Integer.parseInt(data.split(" ")[1]);
                    }

                    String links = "";
                    int i = 0;
                    for(String line: lines) {
                        links += "["+line.trim()+"](//stackoverflow.com/a/"+line.trim()+"); ";
                        i++;
                        if(i==stopValue)
                            break;
                    }
                    room.replyTo(event.getMessage().getId(), links);
                }
            }
            else if(data.split(" ")[0].equals("FMS") && lines.size()!=0) {

                String links = "";
                for(String line: lines) {
                    links += "["+line.trim()+"](http://51.254.218.90:8000/Natty/"+line.trim()+".html); ";
                }
                room.replyTo(event.getMessage().getId(), links);
            }
            else if(data.split(" ")[0].toLowerCase().equals("sentinel") && lines.size()!=0) {

                String links = "";
                for(String line: lines) {
                    String postId = line.trim();
                    String sentinelId = FileUtils.readLineFromFileStartswith(FilePathUtils.outputSentinelIdLogFile,postId);
                    sentinelId = sentinelId.replace(postId+",","");
                    if(sentinelId.equals("-1"))
                        links+= postId+"; ";
                    else
                        links+= "["+postId+"]("+SentinelUtils.sentinelMainUrl+"/posts/"+sentinelId+"); ";
                }
                room.replyTo(event.getMessage().getId(), links);
            }
            else if(data.split(" ")[0].equals("amount") && lines.size()!=0) {
                room.replyTo(event.getMessage().getId(), Integer.toString(lines.size()));
            }
            else {

                if (lines.size() == 0)
                    room.replyTo(event.getMessage().getId(), "All reports have been tended to");
                else
                    room.replyTo(event.getMessage().getId(), returnString);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }

    @Override
    public String name() {
        return "fetch";
    }
}
