package in.bhargavrao.stackoverflow.natty.services;

import in.bhargavrao.stackoverflow.natty.commands.Feedback;
import in.bhargavrao.stackoverflow.natty.exceptions.NoSuchUserFoundException;
import in.bhargavrao.stackoverflow.natty.model.ListType;
import in.bhargavrao.stackoverflow.natty.model.OptedInUser;
import in.bhargavrao.stackoverflow.natty.model.PostReport;
import in.bhargavrao.stackoverflow.natty.model.SOUser;
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
                    optedInUser.setRoomId(Long.valueOf(pieces[3]));
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
    public String saveFeedback(Feedback feedback) {
        return null;
    }

    @Override
    public String storeReport(PostReport report) {
        return null;
    }


    private String getFilename(ListType type) {
        String filename = null;
        switch (type){

            case BLACKLIST: filename = blacklistFile; break;
            case WHITELIST: filename = whitelistFile; break;
            case SALUTE: filename = salutationsFile; break;
        }
        return filename;
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
