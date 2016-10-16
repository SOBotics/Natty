package in.bhargavrao.stackoverflow.natobot.services;

/**
 * Created by bhargav.h on 16-Oct-16.
 */
public class StatsService {
    private static long totalReports=0;
    private static long trueReplies=0;
    private static long falseReplies=0;
    private static long editReplies=0;

    public static void addReport(){
        totalReports++;
    }
    public static void addtp(){
        trueReplies++;
    }
    public static void addfp(){
        falseReplies++;
    }
    public static void addne(){
        editReplies++;
    }
    public static String getStatus(){
        return "tps: "+trueReplies+" nes: "+editReplies+" fps: "+falseReplies + " total: "+totalReports;
    }
}
