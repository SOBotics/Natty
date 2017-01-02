package in.bhargavrao.stackoverflow.natty.commands;

import java.io.IOException;
import java.util.List;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natty.utils.FileUtils;
import in.bhargavrao.stackoverflow.natty.utils.SentinelUtils;

/**
 * Created by bhargav.h on 20-Oct-16.
 */
public class Fetch implements SpecialCommand {

    private Message message;

    public Fetch(Message message) {
        this.message = message;
    }

    @Override
    public boolean validate() {

        return CommandUtils.checkForCommand(message.getPlainContent(),"fetch");
    }

    @Override
    public String description() {
        return "Returns a list of posts that need feedback, See the wiki for more details";
    }

    @Override
    public void execute(Room room) {

        String data = CommandUtils.extractData(message.getPlainContent()).trim();
        String filename = FilePathUtils.outputReportLogFile;

        try{
            List<String> lines = FileUtils.readFile(filename);
            String returnString = String.join("; ", lines);

            if(data.split(" ")[0].equals("posts") && lines.size()<10 && lines.size()!=0){
                room.replyTo(message.getId(), "Deprecated, Please use `fetch links` instead.");
            }
            else if(data.split(" ")[0].equals("links") && lines.size()!=0) {

                if (lines.size() == 0)
                    room.replyTo(message.getId(), "All reports have been tended to");
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
                    room.replyTo(message.getId(), links);
                }
            }
            else if(data.split(" ")[0].equals("FMS") && lines.size()!=0) {

                String links = "";
                for(String line: lines) {
                    links += "["+line.trim()+"](http://51.254.218.90:8000/Natty/"+line.trim()+".html); ";
                }
                room.replyTo(message.getId(), links);
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
                room.replyTo(message.getId(), links);
            }
            else if(data.split(" ")[0].equals("amount") && lines.size()!=0) {
                room.replyTo(message.getId(), Integer.toString(lines.size()));
            }
            else {

                if (lines.size() == 0)
                    room.replyTo(message.getId(), "All reports have been tended to");
                else
                    room.replyTo(message.getId(), returnString);
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
