package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.User;
import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.model.PostReport;
import in.bhargavrao.stackoverflow.natty.services.NattyService;
import in.bhargavrao.stackoverflow.natty.utils.*;
import in.bhargavrao.stackoverflow.natty.validators.Validator;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bhargav.h on 28-Oct-16.
 */
public class Report implements SpecialCommand {

    private Message message;
    private Validator validator;
    private Double naaLimit;
    private String siteName;
    private String siteUrl;

    public Report(Message message, Validator validator, Double naaLimit, String siteName, String siteUrl) {
        this.message = message;
        this.validator = validator;
        this.naaLimit = naaLimit;
        this.siteName = siteName;
        this.siteUrl = siteUrl;
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"report");
    }

    @Override
    public void execute(Room room) {
        try {


            String word = CommandUtils.extractData(message.getPlainContent()).trim();

            if(word.contains("/"))
            {
                if (!word.contains(siteUrl)){
                    room.send("Post is not allowed to be reported in this room.");
                    return;
                }
                word = CommandUtils.getAnswerId(word);
            }

            String outputReportLogFile = FilePathUtils.getOutputReportLogFile(siteName);
            String outputCSVLogFile = FilePathUtils.getOutputCSVLogFile(siteName);
            if(FileUtils.checkIfInFile(outputReportLogFile,word)){
                room.replyTo(message.getId(), "Post already reported");
            }
            else {
                if(FileUtils.readLineFromFileStartswith(outputCSVLogFile,"tp,"+word)!=null) {
                    room.replyTo(message.getId(), "Post already registered as True Positive");
                }
                else if(FileUtils.readLineFromFileStartswith(outputCSVLogFile,"fp,"+word)!=null) {
                    room.replyTo(message.getId(), "Post already registered as False Positive");
                }
                else if(FileUtils.readLineFromFileStartswith(outputCSVLogFile,"ne,"+word)!=null) {
                    room.replyTo(message.getId(), "Post already registered as Needs Edit");
                }
                else if(FileUtils.readLineFromFileStartswith(outputCSVLogFile,"tn,"+word)!=null) {
                    room.replyTo(message.getId(), "Post already registered as True Negative");
                }
                else {
                    NattyService cc = new NattyService(siteName, siteUrl);
                    Post np = cc.checkPost(Integer.parseInt(word));


                    if(validator.validate(np)) {

                        PostReport report = PostUtils.getNaaValue(np);
                        String feedback_type = "tn";

                        if (report.getNaaValue()>naaLimit)
                            feedback_type = "tp";

                        String completeLog = feedback_type+"," + np.getAnswerID() + "," + np.getAnswerCreationDate() + "," + report.getNaaValue() + "," + np.getBodyMarkdown().length() + "," + np.getAnswerer().getReputation() + "," + report.getCaughtFor().stream().collect(Collectors.joining(";")) + ";";
                        FileUtils.appendToFile(outputCSVLogFile, completeLog);

                        long postId = PostUtils.addSentinel(report, siteName, siteUrl);
                        String description;

                        if (postId == -1) {
                            description = ("[ [Natty](" + PrintUtils.printStackAppsPost() + ") | [FMS](" + PostUtils.addFMS(report) + ") ]");
                        } else {
                            description = ("[ [Natty](" + PrintUtils.printStackAppsPost() + ") | [Sentinel](" + SentinelUtils.getSentinelMainUrl(siteName) + "/posts/" + postId + ") ]");
                        }
                        PostPrinter pp = new PostPrinter(np, description);
                        pp.addQuesionLink();

                        Double found = report.getNaaValue();
                        List<String> caughtFilters = report.getCaughtFor();

                        for (String filter : caughtFilters) {
                            pp.addMessage(" **" + filter + "**; ");
                        }

                        pp.addMessage(" **" + found + "**;");

                        User user = message.getUser();
                        PostUtils.addFeedback(postId, user.getId(), user.getName(), feedback_type, siteName, siteUrl);
                        //FileUtils.appendToFile(FilePathUtils.outputSentinelIdLogFile,report.getPost().getAnswerID()+","+postId);

                        room.send(pp.print());
                    }
                    else {
                        room.send("Post is not allowed to be reported in this room.");
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
            room.replyTo(message.getId(), "Error occurred, Try again");
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
