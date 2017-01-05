package in.bhargavrao.stackoverflow.natty.clients;

import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natty.entities.Post;
import in.bhargavrao.stackoverflow.natty.entities.PostReport;
import in.bhargavrao.stackoverflow.natty.entities.OptedInUser;
import in.bhargavrao.stackoverflow.natty.printers.PostPrinter;
import in.bhargavrao.stackoverflow.natty.utils.*;
import in.bhargavrao.stackoverflow.natty.validators.Validator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bhargav.h on 20-Oct-16.
 */
public class Runner {
    public int runOnce(Room room, Validator validator, List<Post> posts, double naaValueLimit, PostPrinter postPrinter, boolean logging){
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
                            FileUtils.appendToFile(FilePathUtils.outputReportLogFile,Integer.toString(np.getAnswerID()));
                            String completeLog = np.getAnswerID()+","+np.getAnswerCreationDate()+","+report.getNaaValue()+","+np.getBodyMarkdown().length()+","+np.getAnswerer().getReputation()+","+report.getCaughtFor().stream().collect(Collectors.joining(";"))+";";
                            FileUtils.appendToFile(FilePathUtils.outputCompleteLogFile,completeLog);
                        }
                        room.send(returnString);
                        numOfAnswers++;
                    }
                }

                if(report.getNaaValue()>=7.0 && logging){
                	
                	/*Boolean isPossibleLinkOnly = false;
                    Boolean hasNoCodeblock = false;
                    Boolean containsBlacklistedWord = false;
                    
                    for(String filter: report.getCaughtFor()){                        
                        //filters to decide which auto-comment to use
                        if (filter.equalsIgnoreCase("No Code Block")) hasNoCodeblock = true;
                        if (filter.equalsIgnoreCase("Possible Link Only")) isPossibleLinkOnly = true;
                        if (filter.equalsIgnoreCase("Contains Blacklisted Word")) containsBlacklistedWord = true;
                    }
                    
                    
                    String comment = "";
                    
                  //decide, which comment to use
                    if (hasNoCodeblock && isPossibleLinkOnly && !containsBlacklistedWord) {
                    	//link-only
                    	System.out.println("link-only");
                    	comment = "link-only";
                    } else {
                    	System.out.println("naa");
                    	comment = "naa";
                    }*/
                	
                	String comment = AutoCommentUtils.commentForPostReport(report);
                	
                	
                    room.send(PostUtils.autoFlag(np, comment));
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return numOfAnswers;
    }


}
