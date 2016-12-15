package in.bhargavrao.stackoverflow.natobot.printers;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.entities.NatoReport;
import in.bhargavrao.stackoverflow.natobot.utils.*;

import java.io.File;
import java.util.List;

/**
 * Created by bhargav.h on 20-Oct-16.
 */
public class SoBoticsPostPrinter implements PostPrinter {

    public final long roomId = 111347;

    @Override
    public String print(NatoReport report) {

        NatoPost np =report.getPost();


        long SentinelId = NatoUtils.addSentinel(report);
        String description;
        if(SentinelId==-1){
            description = ("[ [NATOBot](" + PrintUtils.printStackAppsPost() + ") | [FMS](" + NatoUtils.addFMS(report) + ") ]");
        }
        else {
            description = ("[ [NATOBot](" + PrintUtils.printStackAppsPost() + ") | [Sentinel](" + SentinelUtils.sentinelMainUrl + "/posts/" + SentinelId + ") ]");
        }
        NatoPostPrinter postPrinter = new NatoPostPrinter(np,description).addMainTag().addQuesionLink().addBodyLength().addReputation();

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
