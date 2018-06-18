package in.bhargavrao.stackoverflow.natty.printers;

import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.model.PostReport;
import in.bhargavrao.stackoverflow.natty.utils.PostPrinter;
import in.bhargavrao.stackoverflow.natty.utils.PrintUtils;
import in.bhargavrao.stackoverflow.natty.utils.SentinelUtils;

import java.util.List;

/**
 * Created by bhargav.h on 01-Apr-17.
 */
public class RaidersRoomPostPrinter implements in.bhargavrao.stackoverflow.natty.printers.PostPrinter {

    public final long roomId = 54445;

    @Override
    public String print(PostReport report) {

        Post np =report.getPost();
        Double naaValue = report.getNaaValue();


        String description;

        description = ("[ [Natty](" + PrintUtils.printStackAppsPost() + ") | [Sentinel](" + SentinelUtils.getSentinelMainUrl("askubuntu") + "/posts/aid/" + report.getPost().getAnswerID() + ") ]");


        PostPrinter postPrinter = new PostPrinter(np,description).addMainTag().addDescription().addUserDetails();

        postPrinter.addMessage(" **"+naaValue+"**;");

        return postPrinter.print();

    }
}
