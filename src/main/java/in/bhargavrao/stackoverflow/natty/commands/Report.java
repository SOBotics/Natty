package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.User;
import in.bhargavrao.stackoverflow.natty.model.Feedback;
import in.bhargavrao.stackoverflow.natty.model.*;
import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.NattyService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import in.bhargavrao.stackoverflow.natty.utils.*;
import in.bhargavrao.stackoverflow.natty.validators.Validator;

import java.io.IOException;
import java.util.List;

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

            StorageService service = new FileStorageService();

            if(service.checkIfReported(word, siteName)){
                room.replyTo(message.getId(), "Post already reported");
            }
            else {

                FeedbackType feedback = service.getFeedback(word, siteName);

                if (feedback!=null) {
                    switch (feedback){
                        case TRUE_POSITIVE: room.replyTo(message.getId(), "Post already registered as True Positive"); break;
                        case FALSE_POSITIVE: room.replyTo(message.getId(), "Post already registered as False Positive"); break;
                        case NEEDS_EDITING: room.replyTo(message.getId(), "Post already registered as Needs Editing"); break;
                        case TRUE_NEGATIVE: room.replyTo(message.getId(), "Post already registered as True Negative"); break;
                    }
                }

                else {
                    NattyService cc = new NattyService(siteName, siteUrl);
                    Post np = cc.checkPost(Integer.parseInt(word));


                    if(validator.validate(np)) {

                        User user = message.getUser();
                        PostReport report = PostUtils.getNaaValue(np, siteName);
                        FeedbackType feedback_type = FeedbackType.TRUE_NEGATIVE;

                        if (report.getNaaValue()>naaLimit)
                            feedback_type = FeedbackType.TRUE_POSITIVE;

                        SavedReport savedReport = PostUtils.getReport(np, report);
                        Feedback fb = new Feedback(user.getName(), user.getId(), feedback_type);
                        service.saveFeedback(fb, savedReport, siteName);

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

                        PostUtils.addFeedback(postId, user.getId(), user.getName(), feedback_type.toString(), siteName, siteUrl);

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
