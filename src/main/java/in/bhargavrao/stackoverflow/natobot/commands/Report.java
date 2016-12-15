package in.bhargavrao.stackoverflow.natobot.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.entities.NatoBot;
import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.entities.NatoReport;
import in.bhargavrao.stackoverflow.natobot.utils.*;
import in.bhargavrao.stackoverflow.natobot.validators.Validator;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bhargav.h on 28-Oct-16.
 */
public class Report implements SpecialCommand {

    private PingMessageEvent event;
    private String message;
    private Validator validator;

    public Report(PingMessageEvent event, Validator validator) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
        this.validator = validator;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"report");
    }

    @Override
    public void execute(Room room) {
        try {


            String word = CommandUtils.extractData(message).trim();

            if(word.contains("/"))
            {
                word = CommandUtils.getAnswerId(word);
            }

            if(FileUtils.checkIfInFile(FilePathUtils.outputReportLogFile,word)){
                room.replyTo(event.getMessage().getId(), "Post already reported");
            }
            else if(FileUtils.readLineFromFileStartswith(FilePathUtils.outputCSVLogFile,"tp,"+word)!=null) {
                room.replyTo(event.getMessage().getId(), "Post already registered as True Positive");
            }
            else if(FileUtils.readLineFromFileStartswith(FilePathUtils.outputCSVLogFile,"fp,"+word)!=null) {
                room.replyTo(event.getMessage().getId(), "Post already registered as False Positive");
            }
            else if(FileUtils.readLineFromFileStartswith(FilePathUtils.outputCSVLogFile,"ne,"+word)!=null) {
                room.replyTo(event.getMessage().getId(), "Post already registered as Needs Edit");
            }
            else if(FileUtils.readLineFromFileStartswith(FilePathUtils.outputCSVLogFile,"tn,"+word)!=null) {
                room.replyTo(event.getMessage().getId(), "Post already registered as True Negative");
            }
            else {
                NatoBot cc = new NatoBot();
                NatoPost np = cc.checkNatoPost(Integer.parseInt(word));


                if(validator.validate(np)) {

                    NatoReport report = NatoUtils.getNaaValue(np);

                    String completeLog = "tn," + np.getAnswerID() + "," + np.getAnswerCreationDate() + "," + report.getNaaValue() + "," + np.getBodyMarkdown().length() + "," + np.getAnswerer().getReputation() + "," + report.getCaughtFor().stream().collect(Collectors.joining(";")) + ";";
                    FileUtils.appendToFile(FilePathUtils.outputCSVLogFile, completeLog);

                    long postId = NatoUtils.addSentinel(report);
                    String description;

                    if (postId == -1) {
                        description = ("[ [NATOBot](" + PrintUtils.printStackAppsPost() + ") | [FMS](" + NatoUtils.addFMS(report) + ") ]");
                    } else {
                        description = ("[ [NATOBot](" + PrintUtils.printStackAppsPost() + ") | [Sentinel](" + SentinelUtils.sentinelMainUrl + "/posts/" + postId + ") ]");
                    }
                    NatoPostPrinter pp = new NatoPostPrinter(np, description);
                    pp.addQuesionLink();

                    Double found = report.getNaaValue();
                    List<String> caughtFilters = report.getCaughtFor();

                    for (String filter : caughtFilters) {
                        pp.addMessage(" **" + filter + "**; ");
                    }

                    pp.addMessage(" **" + found + "**;");

                    NatoUtils.addFeedback(postId, event.getUserId(), event.getUserName(), "tn");
                    //FileUtils.appendToFile(FilePathUtils.outputSentinelIdLogFile,report.getPost().getAnswerID()+","+postId);

                    room.send(pp.print());
                }
                else {
                    room.send("Post is not allowed to be reported in this room.");
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
            room.replyTo(event.getMessage().getId(), "Error occurred, Try again");
        }
    }

    @Override
    public String description() {
        return "Reports the mentioned post as a true negative NAA/VLQ";
    }

    @Override
    public String name() {
        return "report";
    }
}
