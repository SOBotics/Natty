package in.bhargavrao.stackoverflow.natty.filters;


import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;

/**
 * Created by bhargav.h on 04-Oct-16.
 */
public class UserMentionedFilter implements Filter {
    private Post post;
    private double value;
    private String userName;

    public UserMentionedFilter(Post post) {
        this.post = post;
        value = 1;
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
