package in.bhargavrao.stackoverflow.natobot.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.services.StatsService;
import in.bhargavrao.stackoverflow.natobot.utils.CommandUtils;
import in.bhargavrao.stackoverflow.natobot.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natobot.utils.FileUtils;
import in.bhargavrao.stackoverflow.natobot.utils.NatoUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by bhargav.h on 23-Oct-16.
 */
public class Send implements SpecialCommand {


    private PingMessageEvent event;
    private String message;

    public Send(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"send") || CommandUtils.checkForCommand(message,"rsend") ;
    }

    @Override
    public void execute(Room room) {
        String data = CommandUtils.extractData(message).trim();
        String feedbacks[] = data.split(" ");

        try{
            List<String> lines = FileUtils.readFile(FilePathUtils.outputCompleteLogFile);
            if(feedbacks.length==0 || (feedbacks.length==1 && feedbacks[0].equals("reverse"))){
                room.replyTo(event.getMessage().getId(), "InputMismatchError, The code has been made Tuna Proof™");
                return;
            }
            if(message.split(" ")[1].toLowerCase().equals("rsend")){
                Collections.reverse(lines);
            }
            if(feedbacks.length>lines.size()){
                room.replyTo(event.getMessage().getId(), "Too many feedbacks, Too less reports");
                return;
            }
            for(int i  =0 ;i<=feedbacks.length;i++){
                String feedback = feedbacks[i].toLowerCase();
                String line = lines.get(i);
                if(feedback.equals("t")) feedback = "tp";
                if(feedback.equals("f")) feedback = "fp";
                if(feedback.equals("n")) feedback = "ne";

                if(feedback.equals("ne")||feedback.equals("tp")||feedback.equals("fp")) {
                    FileUtils.appendToFile(FilePathUtils.outputCSVLogFile, feedback + "," + line);
                    FileUtils.removeFromFile(FilePathUtils.outputCompleteLogFile, line);
                    FileUtils.removeFromFile(FilePathUtils.outputReportLogFile, line.split(",")[0]);
                    String sentinel = FileUtils.readLineFromFileStartswith(FilePathUtils.outputSentinelIdLogFile,line.split(",")[0]);
                    long postId = Long.parseLong(sentinel.split(",")[1]);
                    if(postId!=-1) {
                        long feedbackId = NatoUtils.addFeedback(postId, event.getUserId(), event.getUserName(), feedback);
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public String description() {
        return "Sends a mass feedback, see the wiki for more details";
    }

    @Override
    public String name() {
        return "send";
    }
}
