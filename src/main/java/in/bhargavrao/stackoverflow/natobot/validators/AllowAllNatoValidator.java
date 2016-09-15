package in.bhargavrao.stackoverflow.natobot.validators;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;

/**
 * Created by bhargav.h on 11-Sep-16.
 */
public class AllowAllNatoValidator implements Validator {

    @Override
    public boolean validate(NatoPost natoPost) {
        return natoPost.getAnswerCreationDate().isAfter(natoPost.getQuestionCreationDate().plusSeconds(2592000));
    }
}
