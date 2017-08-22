package in.bhargavrao.stackoverflow.natty.services;

import in.bhargavrao.stackoverflow.natty.exceptions.NoSuchUserFoundException;
import in.bhargavrao.stackoverflow.natty.model.*;
import in.bhargavrao.stackoverflow.natty.utils.FileUtils;
import in.bhargavrao.stackoverflow.natty.utils.JsonUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhargav.h on 20-Aug-17.
 */
public class FileStorageService implements StorageService {

    private String blacklistFile = "./data/BlackListedWords.txt";
    private String whitelistFile = "./data/WhiteListedWords.txt";
    private String salutationsFile = "./data/Salutations.txt";
    private String optedUsersFile = "./data/OptedInUsersList.txt";
    private String featureRequests = "./data/FeatureRequests.txt";
    private String checkUsers = "./data/CheckUsers.txt";
    private String blacklistedUsers = "./data/BlackListedUsers.txt";

    private String outputErrorLogFile = "./logs/error.txt";

    private String loginPropertiesFile = "./properties/login.properties";

    private String outputCSVLogFileName = "output.csv";
    private String outputReportLogFileName = "reports.txt";
    private String outputCompleteLogFileName = "fullReports.txt";
    private String outputSentinelIdLogFileName = "sentinelReports.txt";


    @Override
    public String listWord(String word, ListType type) {

        String filename = getFilename(type);

        try {
            if (FileUtils.checkIfInFile(filename, word)) {
                return "Already Blacklisted";
            }
            else{
                FileUtils.appendToFile(filename,word);
                return  "Added blacklist successfully";
            }
        } catch (IOException e) {
            return "Some error occurred";
        }
    }


    @Override
    public String unListWord(String word, ListType type) {

        String filename = getFilename(type);

        try{
            if(FileUtils.checkIfInFile(filename,word)){
                FileUtils.removeFromFile(filename,word);
                return "Done";
            }
            else {
                return ("It's not there in the file");
            }
        }
        catch (IOException e){
            e.printStackTrace();
            return ("Some Error Occured");
        }

    }

    @Override
    public boolean checkListWord(String word, ListType type){
        try {
            return FileUtils.checkIfInFile(getFilename(type),word);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<String> getListWords(ListType type) {
        try {
            return FileUtils.readFile(getFilename(type));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public String addCheckUsers(Integer userid, String message, String sitename) {
        try {
            FileUtils.appendToFile(checkUsers,userid+","+sitename+","+ JsonUtils.escapeHtmlEncoding(message));
            return "Added user successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Some Error Occured";
        }
    }


    @Override
    public String checkUsers(Integer userid, String sitename) throws NoSuchUserFoundException {

        String filename = checkUsers;

        try {
            for(String line: FileUtils.readFile(filename)){
                String users[] = line.split(",");
                if(users[0].equals(Integer.toString(userid))  && users[1].equals(sitename) ){
                    return users[2];
                }
            }
        } catch (IOException e) {
            return "Some Error Occured.";
        }

        throw new NoSuchUserFoundException();
    }

    @Override
    public List<OptedInUser> getOptedInUsers(String tagname, long roomId) {
        List <OptedInUser> optedInUsers = new ArrayList<>();

        String filename = optedUsersFile;
        try {
            List<String> lines = FileUtils.readFile(filename);
            for(String e:lines){
                String pieces[] = e.split(",");
                if((pieces[1].equals(tagname)||pieces[1].equals("all")) && Long.valueOf(pieces[3]).equals(Long.valueOf(roomId))){

                    OptedInUser optedInUser = new OptedInUser();

                    SOUser SOUser = new SOUser();
                    SOUser.setUsername(pieces[2].replace("\"",""));
                    SOUser.setUserId(Integer.parseInt(pieces[0]));

                    optedInUser.setUser(SOUser);
                    optedInUser.setTagname(pieces[1]);
                    optedInUser.setPostType(pieces[4]);
                    optedInUser.setRoomId(Integer.valueOf(pieces[3]));
                    optedInUser.setWhenInRoom(Boolean.parseBoolean(pieces[5]));


                    optedInUsers.add(optedInUser);
                }
            }
        }
        catch (IOException e){
            System.out.println("file not found");
        }
        return optedInUsers;
    }

    @Override
    public String addOptedInUser(OptedInUser user) {
        String optMessage = getOptMessageFromUser(user);
        try
        {
            if(FileUtils.checkIfInFile(optedUsersFile,optMessage)){
                return "You've already been added.";
            }
            else {
                FileUtils.appendToFile(optedUsersFile, optMessage);
                System.out.println("Added user");
                return  "You've been added.";
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return "Some Error Occurred";
        }
    }

    @Override
    public String removeOptedInUser(OptedInUser user) {
        String optMessage = getOptMessageFromUser(user);
        try
        {
            if(FileUtils.checkIfInFile(optedUsersFile,optMessage)) {
                FileUtils.removeFromFile(optedUsersFile, optMessage);
                System.out.println("Remove user");
                return  "You've been removed.";
            }
            else{
                return "You've already been removed.or you haven't yet opted-in.";
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return "Some Error Occurred";
        }
    }

    @Override
    public String removeAllOptIn(long userId) {
        try {
            FileUtils.removeFromFileStartswith(optedUsersFile,userId+",");
            return "Removed all the tags and rooms where you have opted in";
        }
        catch (IOException e){
            e.printStackTrace();
            return "Some Error Occurred";
        }
    }

    @Override
    public String storeReminders(String reminder) {
        try {
            String filename = featureRequests;
            if (FileUtils.checkIfInFile(filename, reminder)) {
                return  "Already present as FR";
            }
            else{
                FileUtils.appendToFile(filename, reminder);
                return  "Added request successfully";
            }
        }
        catch (IOException e){
            e.printStackTrace();
            return "Some Error Occured";
        }
    }


    @Override
    public List<String> retrieveReminders() {
        try {
            return FileUtils.readFile(featureRequests);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String deleteReminder(int reminderIndex) {
        try {
            FileUtils.removeFromFileLine(featureRequests,reminderIndex);
            return "Request removed";
        } catch (IOException e) {
            e.printStackTrace();
            return "Some Error Occurred";
        }
    }

    @Override
    public String deleteReminders(int[] reminderIndexes) {
        try {
            FileUtils.removeFromFileLines(featureRequests,reminderIndexes);
            return "Requests removed";
        } catch (IOException e) {
            e.printStackTrace();
            return "Some Error Occurred";
        }
    }


    @Override
    public String storeReport(SavedReport report, String sitename) {

        String completeLog = getReportLog(report);

        try {
            FileUtils.appendToFile(getPath(sitename)+outputReportLogFileName, String.valueOf(report.getAnswerId()));
            FileUtils.appendToFile(getPath(sitename)+outputCompleteLogFileName, completeLog);
            return "Successfully stored.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Some Error Occured";
        }
    }

    @NotNull
    private String getReportLog(SavedReport report) {
        return report.getAnswerId()+","+
                    report.getTimestamp()+","+
                    report.getNaaValue()+","+
                    report.getBodyLength()+","+
                    report.getReputation()+","+
                    getReasonString(report.getReasons())+";";
    }

    @Override
    public List<SavedReport> getFullReports(String sitename) {
        return null;
    }

    @Override
    public List<String> getReports(String sitename) {
        try {
            return FileUtils.readFile(getPath(sitename)+outputReportLogFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean checkIfReported(String postId, String sitename) {
        try {
            return FileUtils.checkIfInFile(getPath(sitename)+outputReportLogFileName,sitename);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String retrieveReport(String postId, String sitename) {
        try {
            return FileUtils.readLineFromFileStartswith(getPath(sitename)+outputCompleteLogFileName,postId);
        } catch (IOException e) {
            e.printStackTrace();
            return "Some Error Occured";
        }
    }

    @Override
    public String getSentinelId(String postId, String sitename) {
        try {
            return FileUtils.readLineFromFileStartswith(getPath(sitename)+outputSentinelIdLogFileName,postId);
        } catch (IOException e) {
            e.printStackTrace();
            return "-1";
        }
    }

    @Override
    public String saveFeedback(Feedback feedback, SavedReport report, String sitename) {
        String feedbackMessage = feedback.getFeedbackType().toString()+","+getReportLog(report);
        try {
            FileUtils.appendToFile(getPath(sitename)+outputCSVLogFileName, feedbackMessage);
            FileUtils.removeFromFileStartswith(getPath(sitename)+outputCompleteLogFileName, String.valueOf(report.getAnswerId()));
            FileUtils.removeFromFile(getPath(sitename)+outputReportLogFileName, String.valueOf(report.getAnswerId()));
            return  "Feedback saved successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Some Error Occurred";
        }

    }

    @Override
    public String invalidateFeedback(Feedback feedback, SavedReport report, String sitename) {
        String reportLog = getReportLog(report);
        FeedbackType oldFeedback = getFeedback(String.valueOf(report.getAnswerId()), sitename);
        String feedbackMessage = feedback.getFeedbackType().toString()+","+ reportLog;
        String oldFeedbackMessage = oldFeedback.toString()+","+ reportLog;


        try {
            FileUtils.removeFromFile(getPath(sitename)+outputCSVLogFileName, oldFeedbackMessage);
            FileUtils.appendToFile(getPath(sitename)+outputCSVLogFileName, feedbackMessage);
            return "Invalidated feedback on "+report.getAnswerId();
        } catch (IOException e) {
            e.printStackTrace();
            return "Some Error Occurred";
        }


    }

    @Override
    public FeedbackType getFeedback(String word, String sitename) {

        String outputCSVLogFile = getPath(sitename)+outputCSVLogFileName;
        try {
            for(FeedbackType type: FeedbackType.values()){
                if(FileUtils.readLineFromFileStartswith(outputCSVLogFile,type.toString()+","+word)!=null)
                    return type;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String retrieveFeedback(String postId, String sitename) {
        FeedbackType ft = getFeedback(postId, sitename);
        try {
            return FileUtils.readLineFromFileStartswith(getPath(sitename)+outputCSVLogFileName,ft.toString()+","+postId);
        } catch (IOException e) {
            e.printStackTrace();
            return "Some Error Occured";
        }
    }

    @Override
    public String storeSentinelData(long postId, long sentinelId, String sitename) {
        try{
            FileUtils.appendToFile(getPath(sitename)+outputSentinelIdLogFileName,postId+","+sentinelId);
            return "Stored Successfully";
        }
        catch (IOException e){
            e.printStackTrace();
            return "Some Error Occured";
        }
    }

    private String getOptMessageFromUser(OptedInUser user){

        return user.getUser().getUserId()+","+
                user.getTagname()+",\""+
                user.getUser().getUsername()+"\""+","+
                user.getRoomId()+","+
                user.getPostType()+","+user.isWhenInRoom();

    }


    private String getFilename(ListType type) {
        String filename = null;
        switch (type){

            case BLACKLIST: filename = blacklistFile; break;
            case WHITELIST: filename = whitelistFile; break;
            case SALUTE: filename = salutationsFile; break;
            case USER_BLACKLIST: filename = blacklistedUsers; break;
        }
        return filename;
    }

    private String getReasonString (List<Reason> reasons){
        String retStr = "";
        for (Reason reason: reasons){

            if (reason.getSubReason()==null || reason.getSubReason().trim().equals(""))
                retStr += reason.getReasonName() + ";";
            else
                retStr += reason.getReasonName() + " - " + reason.getSubReason() + ";";
        }
        return retStr;
    }

    @NotNull
    private String getPath(String sitename) {
        String path = "./";
        switch (sitename){
            case "stackoverflow": path+="logs/"; break;
            case "askubuntu"    : path+="aulogs/"; break;
        }
        return path;
    }
}
