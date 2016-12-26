package in.bhargavrao.stackoverflow.natty.printers;

import in.bhargavrao.stackoverflow.natty.entities.Post;
import in.bhargavrao.stackoverflow.natty.entities.PostReport;
import in.bhargavrao.stackoverflow.natty.utils.PostPrinter;
import in.bhargavrao.stackoverflow.natty.utils.PrintUtils;
import in.bhargavrao.stackoverflow.natty.utils.SentinelUtils;

/**
 * Created by bhargav.h on 09-Dec-16.
 */
public class GmtsPostPrinter implements in.bhargavrao.stackoverflow.natty.printers.PostPrinter {

    public final long roomId = 75819;

    @Override
    public String print(PostReport report) {

        Post np =report.getPost();

        String description= ("[ [Natty](" + PrintUtils.printStackAppsPost() + ") | [Sentinel](" + SentinelUtils.sentinelMainUrl + "/posts/aid/" + report.getPost().getAnswerID() + ") ]");

        PostPrinter postPrinter = new PostPrinter(np,description).addMainTag().addQuesionLink().addUserDetails();

        return postPrinter.print();
    }
}
