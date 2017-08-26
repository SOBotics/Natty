package in.bhargavrao.stackoverflow.natty.clients;

import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.model.OptedInUser;
import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.model.PostReport;
import in.bhargavrao.stackoverflow.natty.model.autocomments.AutoComment;
import in.bhargavrao.stackoverflow.natty.printers.PostPrinter;
import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import in.bhargavrao.stackoverflow.natty.services.UserService;
import in.bhargavrao.stackoverflow.natty.utils.AutoCommentUtils;
import in.bhargavrao.stackoverflow.natty.utils.PostUtils;
import in.bhargavrao.stackoverflow.natty.utils.StatusUtils;
import in.bhargavrao.stackoverflow.natty.validators.Validator;

import java.time.Instant;
import java.util.List;


/**
 * Created by bhargav.h on 20-Oct-16.
 */
public class Runner {
    public int runOnce(Room room, Validator validator, List<Post> posts, double naaValueLimit, PostPrinter postPrinter, boolean logging, String sitename, String siteurl){
        Instant startDate = Instant.now();
    	UserService userService = new UserService(room);
    	int numOfAnswers = 0;
        try{
            for (Post np : posts) {

                List<OptedInUser> pingUsersList = userService.pingUserIfApplicable(np);
                PostReport report = PostUtils.getNaaValue(np, sitename);


                if (validator.validate(np)){
                    if (report.getNaaValue()>naaValueLimit) {
                        String returnString = postPrinter.print(report);
                        for (OptedInUser user : pingUsersList) {
                            if (!user.isWhenInRoom() || (user.isWhenInRoom() && userService.checkIfUserInRoom(user.getUser().getUserId()))) {
                                returnString+=(" @"+user.getUser().getUsername().replace(" ",""));
                            }
                        }
                        if(logging){
                            StorageService service = new FileStorageService();
                            service.storeReport(PostUtils.getReport(np, report),sitename);
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
