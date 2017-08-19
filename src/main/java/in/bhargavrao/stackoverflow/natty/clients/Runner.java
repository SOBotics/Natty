package in.bhargavrao.stackoverflow.natty.clients;

import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.model.PostReport;
import in.bhargavrao.stackoverflow.natty.model.autocomments.AutoComment;
import in.bhargavrao.stackoverflow.natty.model.OptedInUser;
import in.bhargavrao.stackoverflow.natty.printers.PostPrinter;
import in.bhargavrao.stackoverflow.natty.utils.*;
import in.bhargavrao.stackoverflow.natty.validators.Validator;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bhargav.h on 20-Oct-16.
 */
public class Runner {
    public int runOnce(Room room, Validator validator, List<Post> posts, double naaValueLimit, PostPrinter postPrinter, boolean logging, String sitename, String siteurl){
        Instant startDate = Instant.now();
    	
    	int numOfAnswers = 0;
        try{
            for (Post np : posts) {

                List<OptedInUser> pingUsersList = UserUtils.pingUserIfApplicable(np,room.getRoomId());
                PostReport report = PostUtils.getNaaValue(np);


                if (validator.validate(np)){
                    if (report.getNaaValue()>naaValueLimit) {
                        String returnString = postPrinter.print(report);
                        for (OptedInUser user : pingUsersList) {
                            if (!user.isWhenInRoom() || (user.isWhenInRoom() && UserUtils.checkIfUserInRoom(room, user.getUser().getUserId()))) {
                                returnString+=(" @"+user.getUser().getUsername().replace(" ",""));
                            }
                        }

                        if(logging){
                            FileUtils.appendToFile(FilePathUtils.getOutputReportLogFile(sitename),Integer.toString(np.getAnswerID()));
                            String completeLog = np.getAnswerID()+","+np.getAnswerCreationDate()+","+report.getNaaValue()+","+np.getBodyMarkdown().length()+","+np.getAnswerer().getReputation()+","+report.getCaughtFor().stream().collect(Collectors.joining(";"))+";";
                            FileUtils.appendToFile(FilePathUtils.getOutputCompleteLogFile(sitename),completeLog);
                        }
                        room.send(returnString);
                        numOfAnswers++;
                    }
                }

                if(validator.validate(np) && report.getNaaValue()>=7.0 && logging && !report.getCaughtFor().contains("Possible Link Only")){
                    // IGNORING LINK ONLY FOR NOW AS THERE ARE A FEW FPs
                    AutoComment comment = AutoCommentUtils.commentForPostReport(report);
                    room.send(PostUtils.autoFlag(np, comment, sitename, siteurl) + " on this [post](//"+siteurl+"/a/"+np.getAnswerID()+")");

                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        //Didn't crash/freeze
        StatusUtils.lastSucceededExecutionStarted = startDate;
        StatusUtils.lastExecutionFinished = Instant.now();
        
        return numOfAnswers;
    }


}
