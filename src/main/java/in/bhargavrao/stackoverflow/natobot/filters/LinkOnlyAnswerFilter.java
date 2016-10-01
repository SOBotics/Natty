package in.bhargavrao.stackoverflow.natobot.filters;


import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.utils.CheckUtils;

/**
 * Created by bhargav.h on 01-Oct-16.
 */
public class LinkOnlyAnswerFilter implements Filter {
    private NatoPost post;
    private double value;

    public LinkOnlyAnswerFilter(NatoPost post) {
        this.post = post;
        value = 2;
    }

    @Override
    public boolean filter() {
        return CheckUtils.checkIfLinkOnlyAnswer(post,40);
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String description() {
        return "Possible Link Only";
    }
}
