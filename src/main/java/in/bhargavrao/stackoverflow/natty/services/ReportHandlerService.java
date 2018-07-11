package in.bhargavrao.stackoverflow.natty.services;

import fr.tunaki.stackoverflow.chat.User;
import in.bhargavrao.stackoverflow.natty.model.FeedbackType;
import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.model.PostReport;
import in.bhargavrao.stackoverflow.natty.model.SavedReport;
import in.bhargavrao.stackoverflow.natty.utils.PostPrinter;
import in.bhargavrao.stackoverflow.natty.utils.PostUtils;
import in.bhargavrao.stackoverflow.natty.utils.PrintUtils;
import in.bhargavrao.stackoverflow.natty.utils.SentinelUtils;
import in.bhargavrao.stackoverflow.natty.validators.Validator;

import java.io.IOException;
import java.util.List;

public class ReportHandlerService {
    private String sitename;
    private String siteurl;
    private Validator validator;
    private Double naaLimit;
    private User user;
    private FeedbackHandlerService feedbackHandlerService;


    public ReportHandlerService(String sitename, String siteurl, Validator validator, Double naaLimit, User user) {
        this.sitename = sitename;
        this.siteurl = siteurl;
        this.validator = validator;
        this.naaLimit = naaLimit;
        this.user = user;
        this.feedbackHandlerService = new FeedbackHandlerService(sitename, siteurl);
    }

    public String reportPost(String answerId){

        NattyService nattyService = new NattyService(sitename, siteurl);
        Post np = null;
        try {
            np = nattyService.checkPost(Integer.parseInt(answerId));
        } catch (IOException e) {
            return "Some Error Occurred";
        }

        if(validator.validate(np)) {

            PostReport report = PostUtils.getNaaValue(np, sitename);
            FeedbackType feedbackType = FeedbackType.TRUE_NEGATIVE;

            if (report.getNaaValue()>naaLimit)
                feedbackType = FeedbackType.TRUE_POSITIVE;

            SavedReport savedReport = PostUtils.getReport(np, report);
            savedReport.setFeedbackType(feedbackType);

            long postId = PostUtils.addSentinel(report, sitename, siteurl);
            String desc;
            if (postId == -1) {
                desc = "[FMS](" + PostUtils.addFMS(report) + ")";
            } else {
                desc = "[Sentinel](" + SentinelUtils.getSentinelMainUrl(sitename) + "/posts/" + postId + ")";
            }
            feedbackHandlerService.handleReportFeedback(user, savedReport, postId, feedbackType);
            return getOutputMessage(np, report, desc);
        }
        else {
            return "Post is not allowed to be reported in this room.";
        }

    }

    private String getOutputMessage(Post np, PostReport report, String dash) {

        String description = ("[ [Natty](" + PrintUtils.printStackAppsPost() + ") | " + dash + " ]");

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

}
