package in.bhargavrao.stackoverflow.natobot.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.entities.NatoBot;
import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.entities.NatoReport;
import in.bhargavrao.stackoverflow.natobot.utils.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bhargav.h on 28-Oct-16.
 */
public class Report implements SpecialCommand {

    private PingMessageEvent event;
    private String message;

    public Report(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"report");
    }

    @Override
    public void execute(Room room) {
        try {
            String word = CommandUtils.extractData(message).trim();

            if(word.contains("/"))
            {
                word = CommandUtils.getAnswerId(word);
            }

            NatoBot cc = new NatoBot();
            NatoPost np = cc.checkNatoPost(Integer.parseInt(word));
            NatoPostPrinter pp = new NatoPostPrinter(np);
            pp.addQuesionLink();

            NatoReport report = NatoUtils.getNaaValue(np);

            Double found = report.getNaaValue();
            List<String> caughtFilters = report.getCaughtFor();

            for(String filter: caughtFilters){
                pp.addMessage(" **"+filter+"**; ");
            }

            pp.addMessage(" **"+found+"**;");

            room.replyTo(event.getMessage().getId(), pp.print());

            String completeLog = "tn,"+np.getAnswerID()+","+np.getAnswerCreationDate()+","+report.getNaaValue()+","+np.getBodyMarkdown().length()+","+np.getAnswerer().getReputation()+","+report.getCaughtFor().stream().collect(Collectors.joining(";"))+";";
            FileUtils.appendToFile(FilePathUtils.outputCSVLogFile,completeLog);

        }
        catch (IOException e){
            e.printStackTrace();
            room.replyTo(event.getMessage().getId(), "Error occured, Try again");
        }
    }
}
