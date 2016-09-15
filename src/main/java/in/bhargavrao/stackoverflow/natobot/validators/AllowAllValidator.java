package in.bhargavrao.stackoverflow.natobot.validators;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;

/**
 * Created by bhargav.h on 13-Sep-16.
 */
public class AllowAllValidator implements Validator{
    @Override
    public boolean validate(NatoPost natoPost) {
        return true;
    }
}
