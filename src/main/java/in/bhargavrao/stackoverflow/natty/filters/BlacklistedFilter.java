package in.bhargavrao.stackoverflow.natty.filters;


import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;

/**
 * Created by bhargav.h on 01-Oct-16.
 */
public class BlacklistedFilter implements Filter {
    private Post post;
    private double value;
    private String listedWord;

    public BlacklistedFilter(Post post) {
        this.post = post;
        value = 2.0;
        listedWord = null;
    }

    @Override
    public boolean filter() {
        String listedWord = CheckUtils.checkForBlackListedWords(post);
        if(listedWord!=null){
            this.listedWord = listedWord;
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
        return "Contains Blacklisted Word - "+listedWord;
    }
}
