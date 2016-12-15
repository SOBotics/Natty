package in.bhargavrao.stackoverflow.natobot.printers;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.entities.NatoReport;
import in.bhargavrao.stackoverflow.natobot.utils.NatoPostPrinter;
import in.bhargavrao.stackoverflow.natobot.utils.NatoUtils;
import in.bhargavrao.stackoverflow.natobot.utils.PrintUtils;
import in.bhargavrao.stackoverflow.natobot.utils.SentinelUtils;

import java.util.List;

/**
 * Created by bhargav.h on 09-Dec-16.
 */
public class GmtsPostPrinter implements PostPrinter {

    public final long roomId = 75819;

    @Override
    public String print(NatoReport report) {

        NatoPost np =report.getPost();

        String description= ("[ [NATOBot](" + PrintUtils.printStackAppsPost() + ") | [Sentinel](" + SentinelUtils.sentinelMainUrl + "/posts/aid/" + report.getPost().getAnswerID() + ") ]");

        NatoPostPrinter postPrinter = new NatoPostPrinter(np,description).addMainTag().addQuesionLink().addUserDetails();

        return postPrinter.print();
    }
}
