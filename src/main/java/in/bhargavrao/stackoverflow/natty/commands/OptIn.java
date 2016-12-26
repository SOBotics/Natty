package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natty.utils.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class OptIn implements SpecialCommand {

    private PingMessageEvent event;
    private String message;

    public OptIn(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getContent();
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"opt-in");
    }

    @Override
    public void execute(Room room) {
        long userId = event.getUserId();
        String userName = event.getUserName();
        String filename = FilePathUtils.optedUsersFile;

        String data = CommandUtils.extractData(message).trim();

        String pieces[] = data.split(" ");

        if(pieces.length>=2){
            String tag = pieces[0];
            String postType = pieces[1];
            boolean whenInRoom = true;
            if(pieces.length==3 && pieces[2].equals("always")){
                whenInRoom = false;
            }

            if(!tag.equals("all")){
                tag = StringUtils.substringBetween(message,"[","]");
            }
            if(postType.equals("all") || postType.equals("naa")){
                String optMessage = userId+","+tag+",\""+userName+"\""+","+room.getRoomId()+","+postType+","+whenInRoom;
                try
                {
                    if(FileUtils.checkIfInFile(filename,optMessage)){
                        room.replyTo(event.getMessage().getId(), "You've already been added.");
                    }
                    else {
                        FileUtils.appendToFile(filename, optMessage);
                        System.out.println("Added user");
                        room.replyTo(event.getMessage().getId(), "You've been added.");
                    }
                }
                catch(IOException e)
                {
                    System.out.println("File not found");
                }
            }
            else {
                room.replyTo(event.getMessage().getId(), "Type of post can be naa or all");
            }
        }
        else if(pieces.length==1){
            room.replyTo(event.getMessage().getId(), "Please specify the type of post.");
        }
    }

    @Override
    public String description() {
        return "Notifies the user of the requested posts, see the wiki for more details";
    }

    @Override
    public String name() {
        return "opt-in";
    }
}
