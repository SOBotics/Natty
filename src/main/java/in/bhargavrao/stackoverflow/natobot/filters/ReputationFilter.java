package in.bhargavrao.stackoverflow.natobot.filters;


import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.utils.CheckUtils;

/**
 * Created by bhargav.h on 12-Oct-16.
 */
public class ReputationFilter implements Filter {
    private NatoPost post;
    private double value;

    public ReputationFilter(NatoPost post) {
        this.post = post;
        value = 1;
    }

    @Override
    public boolean filter() {
        return post.getAnswerer().getReputation()<1001;
    }

    @Override
    public double getValue() {
        long rep = post.getAnswerer().getReputation();
        if(rep < 51){
            return 1.0;
        }
        else if(rep < 1001){
            return 0.5;
        }
        else {
            return 0.0;
        }
    }

    @Override
    public String description() {
        return "Low Rep";
    }
}
