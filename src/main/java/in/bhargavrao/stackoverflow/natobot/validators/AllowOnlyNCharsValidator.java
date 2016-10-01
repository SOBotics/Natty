package in.bhargavrao.stackoverflow.natobot.validators;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;

/**
 * Created by bhargav.h on 12-Sep-16.
 */
public class AllowOnlyNCharsValidator implements Validator {

    private int len;

    public AllowOnlyNCharsValidator(int len) {
        this.len = len;
    }

    @Override
    public boolean validate(NatoPost natoPost) {
        return natoPost.getBody().length()<len;
    }

}
