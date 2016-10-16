package in.bhargavrao.stackoverflow.natobot.filters;


import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.utils.CheckUtils;

/**
 * Created by bhargav.h on 01-Oct-16.
 */
public class SalutationsFilter implements Filter {
    private NatoPost post;
    private double value;
    private String salutation;

    public SalutationsFilter(NatoPost post) {
        this.post = post;
        value = 1.5;
        salutation = null;
    }

    @Override
    public boolean filter() {
        String salutation = CheckUtils.checkForSalutation(post);
        if(salutation!=null){
            this.salutation = salutation;
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
        return "Contains Salutation - "+salutation;
    }
}
