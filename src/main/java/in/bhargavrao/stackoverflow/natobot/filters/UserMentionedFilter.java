package in.bhargavrao.stackoverflow.natobot.filters;


import in.bhargavrao.stackoverflow.natobot.commands.Check;
import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.utils.CheckUtils;

/**
 * Created by bhargav.h on 04-Oct-16.
 */
public class UserMentionedFilter implements Filter {
    private NatoPost post;
    private double value;
    private String userName;

    public UserMentionedFilter(NatoPost post) {
        this.post = post;
        value = 0.5;
        userName = null;
    }

    @Override
    public boolean filter() {
        String userName = CheckUtils.checkIfBodyStartsWithMention(post);
        if(userName!=null){
            this.userName = userName.trim().replace("\n","");
            return true;
        }
        return false;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String description() {
        return "User "+userName+" Mentioned";
    }
}
