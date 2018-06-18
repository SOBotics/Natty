package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Message;
import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.User;
import in.bhargavrao.stackoverflow.natty.exceptions.FeedbackInvalidatedException;
import in.bhargavrao.stackoverflow.natty.model.*;
import in.bhargavrao.stackoverflow.natty.services.FeedbackHandlerService;
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
    private FeedbackHandlerService feedbackHandlerService;

    public Report(Message message, Validator validator, Double naaLimit, String siteName, String siteUrl) {
        this.message = message;
        this.validator = validator;
        this.naaLimit = naaLimit;
        this.siteName = siteName;
        this.siteUrl = siteUrl;
        this.feedbackHandlerService = new FeedbackHandlerService(siteName, siteUrl);
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message.getPlainContent(),"report");
    }

    @Override
    public void execute(Room room) {
        try {


            String word = CommandUtils.extractData(message.getPlainContent()).trim();
            User user = message.getUser();
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
                feedbackHandlerService.handleFeedback(user, "tp", word);
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
                    feedbackHandlerService.handleFeedback(user, "tp", word);
                }

                else {
                    NattyService nattyService = new NattyService(siteName, siteUrl);
                    Post np = nattyService.checkPost(Integer.parseInt(word));

                    if(validator.validate(np)) {


                        PostReport report = PostUtils.getNaaValue(np, siteName);
                        FeedbackType feedbackType = FeedbackType.TRUE_NEGATIVE;

                        if (report.getNaaValue()>naaLimit)
                            feedbackType = FeedbackType.TRUE_POSITIVE;

                        SavedReport savedReport = PostUtils.getReport(np, report);

                        long postId = PostUtils.addSentinel(report, siteName, siteUrl);
                        room.send(getOutputMessage(np, report, postId));
                        feedbackHandlerService.handleReport(user, savedReport, postId, feedbackType);
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
        } catch (FeedbackInvalidatedException e) {
            e.printStackTrace();
        }
    }

    private String getOutputMessage(Post np, PostReport report, long postId) {
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

        return pp.print();
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
