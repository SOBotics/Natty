package in.bhargavrao.stackoverflow.natobot.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natobot.entities.NatoBot;
import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.entities.NatoReport;
import in.bhargavrao.stackoverflow.natobot.utils.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by bhargav.h on 30-Sep-16.
 */
public class Check implements SpecialCommand {

    private PingMessageEvent event;
    private String message;

    public Check(PingMessageEvent event) {
        this.event = event;
        this.message = event.getMessage().getPlainContent();
    }

    @Override
    public boolean validate() {
        return CommandUtils.checkForCommand(message,"check");
    }

    @Override
    public void execute(Room room) {
        try {
            String filename = FilePathUtils.checkUsers;
            String word = CommandUtils.extractData(message).trim();
            Integer returnValue = 0;

            if(word.contains(" ")){
                String parts[] = word.split(" ");
                if(parts[0].toLowerCase().equals("value")){
                    returnValue = 1;
                    word = parts[1];
                }
                else if (parts[0].toLowerCase().equals("explain")){
                    returnValue = 2;
                    word = parts[1];
                }
            }
            if(word.contains("/"))
            {
                String parts[]= word.split("//")[1].split("/");
                if(parts[1].equals("a") || parts[1].equals("answers")){
                    word = parts[2];
                }
                else if (parts[1].equals("q") || parts[1].equals("questions")){
                    if (parts[4].contains("#"))
                    {
                        word = parts[4].split("#")[1];
                    }
                }
                else if(parts[1].equals("users")){
                    for(String line: FileUtils.readFile(filename)){
                        String users[] = line.split(",");
                        if(parts[2].equals(users[0])){
                            room.replyTo(event.getMessage().getId(), users[1]);
                        }
                    }
                }
            }

            NatoBot cc = new NatoBot();
            NatoPost np = cc.checkNatoPost(Integer.parseInt(word));
            NatoPostPrinter pp = new NatoPostPrinter(np);
            pp.addQuesionLink();

            NatoReport report = NatoUtils.getNaaValue(np);

            Double found = report.getNaaValue();
            List<String> caughtFilters = report.getCaughtFor();
            List<Double> caughtFiltersValues = report.getCaughtForValues();

            for(String filter: caughtFilters){
                pp.addMessage(" **"+filter+"**; ");
            }

            pp.addMessage(" **"+found+"**;");
            if(returnValue==1) {
                room.replyTo(event.getMessage().getId(), "The NAA Value is " + found);
            }
            if(returnValue==2) {
                room.replyTo(event.getMessage().getId(), "The NAA Value is " + found + ". The explanation for the filters is:");
                String explanation = "";
                for(int i=0;i<caughtFilters.size();i++){
                    explanation+="    "+caughtFiltersValues.get(i)+" - "+caughtFilters.get(i)+"\n";
                }
                room.send(explanation);
            }
            else {
                room.replyTo(event.getMessage().getId(), pp.print());
            }
        }
        catch (IOException e){
            e.printStackTrace();
            room.replyTo(event.getMessage().getId(), "Error occured, Try again");
        }
    }
}
