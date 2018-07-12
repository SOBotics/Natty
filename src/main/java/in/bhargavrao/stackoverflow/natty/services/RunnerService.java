package in.bhargavrao.stackoverflow.natty.services;

import in.bhargavrao.stackoverflow.natty.clients.Runner;
import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.roomdata.BotRoom;
import in.bhargavrao.stackoverflow.natty.validators.AllowAllAnswersValidator;
import org.sobotics.chatexchange.chat.Room;
import org.sobotics.chatexchange.chat.StackExchangeClient;
import org.sobotics.chatexchange.chat.event.EventType;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static in.bhargavrao.stackoverflow.natty.utils.PostUtils.newMessage;

/**
 * Created by bhargav.h on 28-Dec-16.
 */
public class RunnerService {

    private StackExchangeClient client;
    private List<BotRoom> rooms;
    private List<Room> chatRooms;
    private Map<String,NattyService> bots;
    private ScheduledExecutorService executorService;
    private ScheduledFuture<?> handle;
    private int presentInterval;
    private long  executionCount;


    public RunnerService(StackExchangeClient client, List<BotRoom> rooms) {
        this.client = client;
        this.rooms = rooms;
        chatRooms = new ArrayList<>();
        bots = new HashMap<>();
        executionCount = 0;
        presentInterval = 59;
    }

    public void start(){
        for(BotRoom room:rooms){

            Room chatroom = null;
            try {
                chatroom = client.joinRoom(room.getHost(), room.getRoomId());
            }
            catch (Exception e){
                e.printStackTrace();
            }

            if(room.getRoomId()==111347 || room.getRoomId()==167908){
                //check if Natty is running on the server
                PropertyService service  = new PropertyService();

                Room finalChatroom = chatroom;
                chatroom.addEventListener(EventType.MESSAGE_POSTED, event-> newMessage(finalChatroom, event, false));

                BlacklistDataService blacklistDataService = new BlacklistDataService(finalChatroom);
                blacklistDataService.start();

                if (service.getLocation().equals("server")) {
                    chatroom.send("Hiya o/ (SERVER VERSION)" );
                    FeederService feederService = new FeederService("*Buys food, but feeds no one. I'm hungry too*",chatroom,8);
                    feederService.start();
                    SelfCheckService selfCheck = new SelfCheckService(this);
                    selfCheck.start();
                    CleanerService cleanerService = new CleanerService(chatroom);
                    cleanerService.start();
                    MentionService mentionService = new MentionService(chatroom);
                    mentionService.start();
                } else {
                    chatroom.send("Hiya o/ (DEVELOPMENT VERSION; "+service.getLocation()+")" );
                }

            }



            chatRooms.add(chatroom);
            if(room.getMention(chatroom,this)!=null)
                chatroom.addEventListener(EventType.USER_MENTIONED, room.getMention(chatroom,this));
            if(room.getReply(chatroom)!=null)
                chatroom.addEventListener(EventType.MESSAGE_REPLY, room.getReply(chatroom));

            String site = room.getSiteName();
            String url = room.getSiteUrl();
            if (!bots.containsKey(site)){
                bots.put(site, new NattyService(site, url));
            }

        }
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void run() {
        handle = executorService.scheduleAtFixedRate(() -> execute(), 0, presentInterval, TimeUnit.SECONDS);
    }

    private void execute() {

        Map<String, List<Post>> postsMap = new HashMap<>();

        for(String sitename: bots.keySet()){
            try {
                postsMap.put(sitename, bots.get(sitename).getPosts(new AllowAllAnswersValidator()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(int i=0;i<rooms.size();i++){
            BotRoom room = rooms.get(i);
            Room chatroom = chatRooms.get(i);
            List<Post> posts = postsMap.get(room.getSiteName());
            new Runner().runOnce(chatroom, room.getValidator(), posts, room.getNaaValue(), room.getPostPrinter(), room.getIsLogged(), room.getSiteName(), room.getSiteUrl());
        }
        executionCount++;
    }

    public void stop(){
        executorService.shutdown();
    }

    public void reboot(){

        this.stop();
        executorService = Executors.newSingleThreadScheduledExecutor();
        this.run();
        for(int i = 0;i<rooms.size(); i++){
            if(rooms.get(i).getIsLogged()) {
                Room room = chatRooms.get(i);
                room.send("Rebooted at " + Instant.now());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
