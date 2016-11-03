package in.bhargavrao.stackoverflow.natobot.filters;


import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.utils.CheckUtils;

/**
 * Created by bhargav.h on 28-Oct-16.
 */
public class UnformattedCodeFilter implements Filter {
    private NatoPost post;
    private double value;

    public UnformattedCodeFilter(NatoPost post) {
        this.post = post;
        value = -0.5;
    }

    @Override
    public boolean filter() {
        return CheckUtils.checkIfUnformatted(post);
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String description() {
        return "Unformatted Code";
    }
}
