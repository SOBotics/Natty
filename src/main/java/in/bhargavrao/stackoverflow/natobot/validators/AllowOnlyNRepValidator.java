package in.bhargavrao.stackoverflow.natobot.validators;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;

/**
 * Created by bhargav.h on 11-Sep-16.
 */
public class AllowOnlyNRepValidator implements Validator {

    private int rep;

    public AllowOnlyNRepValidator(int rep) {
        this.rep = rep;
    }

    @Override
    public boolean validate(NatoPost natoPost) {
        return natoPost.getAnswerer().getReputation() <= rep;
    }

}
