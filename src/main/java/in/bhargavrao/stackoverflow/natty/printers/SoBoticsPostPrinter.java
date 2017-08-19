package in.bhargavrao.stackoverflow.natty.printers;

import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.model.PostReport;
import in.bhargavrao.stackoverflow.natty.utils.*;

import java.util.List;

/**
 * Created by bhargav.h on 20-Oct-16.
 */
public class SoBoticsPostPrinter implements PostPrinter {

    public final long roomId = 111347;

    @Override
    public String print(PostReport report) {

        Post np =report.getPost();


        long SentinelId = PostUtils.addSentinel(report, "stackoverflow", "stackoverflow.com");
        String description;
        if(SentinelId==-1){
            description = ("[ [NattyService](" + PrintUtils.printStackAppsPost() + ") | [FMS](" + PostUtils.addFMS(report) + ") ]");
        }
        else {
            description = ("[ [NattyService](" + PrintUtils.printStackAppsPost() + ") | [Sentinel](" + SentinelUtils.getSentinelMainUrl("stackoverflow") + "/posts/" + SentinelId + ") ]");
        }
        in.bhargavrao.stackoverflow.natty.utils.PostPrinter postPrinter = new in.bhargavrao.stackoverflow.natty.utils.PostPrinter(np,description).addMainTag().addQuesionLink().addBodyLength().addReputation();

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
