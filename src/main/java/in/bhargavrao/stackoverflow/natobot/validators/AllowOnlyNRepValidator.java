package in.bhargavrao.stackoverflow.natobot.validators;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;

/**
 * Created by bhargav.h on 11-Sep-16.
 */
public class AllowOnlyNRepValidator implements Validator {

    private Integer rep;

    public AllowOnlyNRepValidator(Integer rep) {
        this.rep = rep;
    }

    @Override
    public boolean validate(NatoPost natoPost) {
        return new AllowAllNatoValidator().validate(natoPost) && natoPost.getReputation().equals(rep);
    }
}
