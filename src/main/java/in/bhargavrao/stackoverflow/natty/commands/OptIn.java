package in.bhargavrao.stackoverflow.natty.commands;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.User;
import in.bhargavrao.stackoverflow.natty.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natty.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natty.utils.FileUtils;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class OptIn implements SpecialCommand {

    private Message message;

    public OptIn(Message message) {
        this.message = message;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"opt-in");
    }

    @Override
    public void execute(Room room) {
    	User user = message.getUser();
        long userId = user.getId();
        String userName = user.getName();
        String filename = FilePathUtils.optedUsersFile;

        String data = CommandUtils.extractData(message.getPlainContent()).trim();

        String pieces[] = data.split(" ");

        if(pieces.length>=2){
            String tag = pieces[0];
            String postType = pieces[1];
            boolean whenInRoom = true;
            if(pieces.length==3 && pieces[2].equals("always")){
                whenInRoom = false;
            }

            if(!tag.equals("all")){
                tag = StringUtils.substringBetween(message.getPlainContent(),"[","]");
            }
            if(postType.equals("all") || postType.equals("naa")){
                String optMessage = userId+","+tag+",\""+userName+"\""+","+room.getRoomId()+","+postType+","+whenInRoom;
                try
                {
                    if(FileUtils.checkIfInFile(filename,optMessage)){
                        room.replyTo(message.getId(), "You've already been added.");
                    }
                    else {
                        FileUtils.appendToFile(filename, optMessage);
                        System.out.println("Added user");
                        room.replyTo(message.getId(), "You've been added.");
                    }
                }
                catch(IOException e)
                {
                    System.out.println("File not found");
                }
            }
            else {
                room.replyTo(message.getId(), "Type of post can be naa or all");
            }
        }
        else if(pieces.length==1){
            room.replyTo(message.getId(), "Please specify the type of post.");
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
