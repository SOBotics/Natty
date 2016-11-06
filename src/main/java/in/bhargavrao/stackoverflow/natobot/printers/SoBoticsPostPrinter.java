package in.bhargavrao.stackoverflow.natobot.printers;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.entities.NatoReport;
import in.bhargavrao.stackoverflow.natobot.utils.*;

import java.util.List;

/**
 * Created by bhargav.h on 20-Oct-16.
 */
public class SoBoticsPostPrinter implements PostPrinter {

    public final long roomId = 111347;

    @Override
    public String print(NatoReport report) {

        NatoPost np =report.getPost();
        NatoPostPrinter postPrinter = new NatoPostPrinter(np).addMainTag().addQuesionLink().addBodyLength().addReputation();

        Double naaValue = report.getNaaValue();
        List<String> caughtFilters = report.getCaughtFor();

        for(String filter: caughtFilters){
            postPrinter.addMessage(" **"+filter+"**; ");
        }

        postPrinter.addFirstLine();

        postPrinter.addMessage(" **"+naaValue+"**;");

        //postPrinter.addMessage("[FMS]("+NatoUtils.addFMS(report)+")");
        postPrinter.addMessage("[Sentinel]("+ SentinelUtils.sentinelMainUrl+"/posts/"+NatoUtils.addSentinel(report)+")");

        return postPrinter.print();
    }
}
