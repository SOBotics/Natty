package in.bhargavrao.stackoverflow.natobot.clients;

import fr.tunaki.stackoverflow.chat.Room;
import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.entities.NatoReport;
import in.bhargavrao.stackoverflow.natobot.entities.OptedInUser;
import in.bhargavrao.stackoverflow.natobot.printers.PostPrinter;
import in.bhargavrao.stackoverflow.natobot.utils.FilePathUtils;
import in.bhargavrao.stackoverflow.natobot.utils.FileUtils;
import in.bhargavrao.stackoverflow.natobot.utils.NatoUtils;
import in.bhargavrao.stackoverflow.natobot.utils.UserUtils;
import in.bhargavrao.stackoverflow.natobot.validators.Validator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bhargav.h on 20-Oct-16.
 */
public class Runner {
    public int runOnce(Room room, Validator validator, List<NatoPost> natoAnswers, double naaValueLimit, PostPrinter postPrinter, boolean logging){
        int numOfAnswers = 0;
        try{
            for (NatoPost np : natoAnswers) {

                List<OptedInUser> pingUsersList = UserUtils.pingUserIfApplicable(np,room.getRoomId());
                NatoReport report = NatoUtils.getNaaValue(np);

                String returnString = postPrinter.print(report);

                if (validator.validate(np)){

                    for (OptedInUser user : pingUsersList) {
                        if (!user.isWhenInRoom() || (user.isWhenInRoom() && UserUtils.checkIfUserInRoom(room, user.getUser().getUserId()))) {
                            returnString+=(" @"+user.getUser().getUsername().replace(" ",""));
                        }
                    }

                    if (report.getNaaValue()>naaValueLimit) {
                        if(logging){
                            FileUtils.appendToFile(FilePathUtils.outputReportLogFile,Integer.toString(np.getAnswerID()));
                            String completeLog = np.getAnswerID()+","+np.getAnswerCreationDate()+","+report.getNaaValue()+","+np.getBodyMarkdown().length()+","+np.getAnswerer().getReputation()+","+report.getCaughtFor().stream().collect(Collectors.joining(";"))+";";
                            FileUtils.appendToFile(FilePathUtils.outputCompleteLogFile,completeLog);
                        }
                        room.send(returnString);
                        numOfAnswers++;
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return numOfAnswers;
    }
}
