package in.bhargavrao.stackoverflow.natty.filters;


import in.bhargavrao.stackoverflow.natty.entities.Post;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;

/**
 * Created by bhargav.h on 01-Oct-16.
 */
public class WhitelistedFilter implements Filter {
    private Post post;
    private double value;
    private String listedWord;

    public WhitelistedFilter(Post post) {
        this.post = post;
        value = -1;
        listedWord = null;
    }

    @Override
    public boolean filter() {
        String listedWord = CheckUtils.checkForWhiteListedWords(post);
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
        return "Contains Whitelisted Word - "+listedWord;
    }
}
