package in.bhargavrao.stackoverflow.natty.commands;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import in.bhargavrao.stackoverflow.natty.entities.Natty;
import in.bhargavrao.stackoverflow.natty.entities.Post;
import in.bhargavrao.stackoverflow.natty.entities.PostReport;
import in.bhargavrao.stackoverflow.natty.utils.*;

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
    	System.out.println("Executing...");
        try {
            String filename = FilePathUtils.checkUsers;
            String word = CommandUtils.extractData(message).trim();
            Integer returnValue = 0;

            System.out.println("point1");
            
            if(word.contains(" ")){
                String parts[] = word.split(" ");
                if(parts[0].toLowerCase().equals("value")){
                    returnValue = 1;
                    word = parts[1];
                    System.out.println("point2.1");
                }
                else if (parts[0].toLowerCase().equals("explain")){
                    returnValue = 2;
                    word = parts[1];
                    System.out.println("point2.2");
                }
            }
            if(word.contains("/"))
            {
            	System.out.println("point3.0");
            	
                String parts[]= word.split("//")[1].split("/");
                if(parts[1].equals("users")){
                    for(String line: FileUtils.readFile(filename)){
                        String users[] = line.split(",");
                        if(parts[2].equals(users[0])){
                            room.replyTo(event.getMessage().getId(), users[1]);
                        }
                    }
                    System.out.println("point3.1");
                }
                else {
                    word = CommandUtils.getAnswerId(word);
                    System.out.println("point3.2");
                }
            }

            
            System.out.println("point4");
            
            Natty cc = new Natty();
            Post np = cc.checkPost(Integer.parseInt(word));
            PostPrinter pp = new PostPrinter(np);
            pp.addQuesionLink();

            PostReport report = PostUtils.getNaaValue(np);

            Double found = report.getNaaValue();
            List<String> caughtFilters = report.getCaughtFor();
            List<Double> caughtFiltersValues = report.getCaughtForValues();

            for(String filter: caughtFilters){
                pp.addMessage(" **"+filter+"**; ");
            }
            
            System.out.println("point5");

            pp.addMessage(" **"+found+"**;");
            if(returnValue==1) {
            	System.out.println("point6");
                room.replyTo(event.getMessage().getId(), "The NAA Value is " + found);
            }
            if(returnValue==2) {
            	System.out.println("point7");
                room.replyTo(event.getMessage().getId(), "The NAA Value is " + found + ". The explanation for the filters is:");
                String explanation = "";
                for(int i=0;i<caughtFilters.size();i++){
                    explanation+="    "+caughtFiltersValues.get(i)+" - "+caughtFilters.get(i)+"\n";
                }
                room.send(explanation);
            }
            else {
            	System.out.println("point8");
                room.replyTo(event.getMessage().getId(), pp.print());
            }
        }
        catch (IOException e){
        	System.out.println("ERROR");
            e.printStackTrace();
            room.replyTo(event.getMessage().getId(), "Error occured, Try again");
        }
    }

    @Override
    public String description() {
        return "Checks the sanity of a given post";
    }

    @Override
    public String name() {
        return "check";
    }
}
