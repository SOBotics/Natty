package in.bhargavrao.stackoverflow.natobot.filters;


import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.utils.CheckUtils;

/**
 * Created by bhargav.h on 16-Oct-16.
 */
public class SelfAnswerFilter implements Filter {
    private NatoPost post;
    private double value;

    public SelfAnswerFilter(NatoPost post) {
        this.post = post;
        value = -3;
    }

    @Override
    public boolean filter() {
        return post.getAnswerer().getUserId()==post.getAsker().getUserId();
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String description() {
        return "Self Answer";
    }
}
