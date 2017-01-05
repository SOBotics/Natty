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
public class OptOut implements SpecialCommand {

    private Message message;

    public OptOut(Message message) {
        this.message = message;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"opt-out");
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
                    if(FileUtils.checkIfInFile(filename,optMessage)) {
                        FileUtils.removeFromFile(filename, optMessage);
                        System.out.println("Remove user");
                        room.replyTo(message.getId(), "You've been removed.");
                    }
                    else{
                        room.replyTo(message.getId(), "You've already been removed.");
                        room.replyTo(message.getId(), "Or did you not opt-in only? o_O");
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
        else if(pieces[0].equals("everything")){
            try {
                FileUtils.removeFromFileStartswith(filename,userId+",");
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else if(pieces.length==1){
            room.replyTo(message.getId(), "Please specify the type of post.");
        }
    }

    @Override
    public String description() {
        return "Unnotifies the user. ";
    }

    @Override
    public String name() {
        return "opt-out";
    }
}
