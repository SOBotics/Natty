package in.bhargavrao.stackoverflow.natty.services;

import java.time.Instant;

/**
 * Created by bhargav.h on 16-Oct-16.
 */
public class StatsService {
    private long totalReports=0;
    private long trueReplies=0;
    private long falseReplies=0;
    private long editReplies=0;
    private long totalAnswersScanned=0;
    private Instant startTime;

    public StatsService(Instant startTime){
        this.startTime = startTime;
    }

    public void addReports(int number){
        totalReports+=number;
    }
    public void addScanned(int number){
        totalAnswersScanned+=number;
    }
    public void addtp(){
        trueReplies++;
    }
    public void addfp(){
        falseReplies++;
    }
    public void addne(){
        editReplies++;
    }
    public String getStatus(){
        return "running since: "+startTime+
                " tps: "+trueReplies+
                " nes: "+editReplies+
                " fps: "+falseReplies+
                " total: "+totalReports+
                " scanned: "+totalAnswersScanned;
    }
}
