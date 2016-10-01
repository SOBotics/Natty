package in.bhargavrao.stackoverflow.natobot.filters;


import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.utils.CheckUtils;

/**
 * Created by bhargav.h on 01-Oct-16.
 */
public class WhitelistedFilter implements Filter {
    private NatoPost post;
    private double value;

    public WhitelistedFilter(NatoPost post) {
        this.post = post;
        value = -1;
    }

    @Override
    public boolean filter() {
        return CheckUtils.checkForWhiteListedWords(post);
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String description() {
        return "Contains Whitelisted Word";
    }
}
