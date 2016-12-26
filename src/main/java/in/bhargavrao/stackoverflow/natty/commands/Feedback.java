package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natty.utils.*;

/**
 * Created by bhargav.h on 29-Nov-16.
 */
public class Feedback implements SpecialCommand {

    private PingMessageEvent event;
    private String message;

    public Feedback(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"feedback");
    }

    @Override
    public void execute(Room room) {
        String args[] = CommandUtils.extractData(message).trim().split(" ");

        if(args.length!=2){
            room.send("Error in arguments passed");
            return;
        }

        String word = args[0];
        String type = args[1];

        if(word.contains("/"))
        {
            word = CommandUtils.getAnswerId(word);
        }

        if(type.equals("tp")||type.equals("fp")||type.equals("ne")||type.equals("t")||type.equals("f")||type.equals("n")) {
            PostUtils.handleFeedback(event, type, word);
        }
        else{
            room.send("Wrong feedback type");
        }
    }

    @Override
    public String description() {
        return "Provides feedback on a given post";
    }

    @Override
    public String name() {
        return "feedback";
    }
}
