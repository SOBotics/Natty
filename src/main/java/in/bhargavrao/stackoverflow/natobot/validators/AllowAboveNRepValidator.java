package in.bhargavrao.stackoverflow.natobot.validators;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;

/**
 * Created by bhargav.h on 11-Sep-16.
 */
public class AllowAboveNRepValidator implements Validator {

    private int rep;

    public AllowAboveNRepValidator(int rep) {
        this.rep = rep;
    }

    @Override
    public boolean validate(NatoPost natoPost) {
        return natoPost.getAnswerer().getReputation() >= rep;
    }

}
