package in.bhargavrao.stackoverflow.natobot.validators;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;

/**
 * Created by bhargav.h on 11-Sep-16.
 */
public class RPublicValidator implements Validator {

    @Override
    public boolean validate(NatoPost natoPost) {
        return new AllowAboveNRepValidator(10000).validate(natoPost) &&
               new AllowOnlyTagValidator("r").validate(natoPost);
    }

}
