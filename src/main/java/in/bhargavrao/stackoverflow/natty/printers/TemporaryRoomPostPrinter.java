package in.bhargavrao.stackoverflow.natty.printers;

import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.model.PostReport;
import in.bhargavrao.stackoverflow.natty.utils.PostUtils;
import in.bhargavrao.stackoverflow.natty.utils.PrintUtils;
import in.bhargavrao.stackoverflow.natty.utils.SentinelUtils;
import in.bhargavrao.stackoverflow.natty.utils.PostPrinter;

import java.util.List;

/**
 * Created by bhargav.h on 01-Apr-17.
 */
public class TemporaryRoomPostPrinter implements in.bhargavrao.stackoverflow.natty.printers.PostPrinter {

    public final long roomId = 54445;

    @Override
    public String print(PostReport report) {

        Post np =report.getPost();

        long SentinelId = PostUtils.addSentinel(report, "askubuntu", "askubuntu.com");
        String description;
        if(SentinelId==-1){
            description = ("[ [Natty](" + PrintUtils.printStackAppsPost() + ") | [FMS](" + PostUtils.addFMS(report) + ") ]");
        }
        else {
            description = ("[ [Natty](" + PrintUtils.printStackAppsPost() + ") | [Sentinel](" + SentinelUtils.getSentinelMainUrl("askubuntu") + "/posts/" + SentinelId + ") ]");
        }
        PostPrinter postPrinter = new PostPrinter(np,description).addMainTag().addQuesionLink().addBodyLength().addReputation();

        Double naaValue = report.getNaaValue();
        List<String> caughtFilters = report.getCaughtFor();

        for(String filter: caughtFilters){
            postPrinter.addMessage(" **"+filter+"**; ");
        }

        postPrinter.addFirstLine();

        postPrinter.addMessage(" **"+naaValue+"**;");

        return postPrinter.print();

    }
}
