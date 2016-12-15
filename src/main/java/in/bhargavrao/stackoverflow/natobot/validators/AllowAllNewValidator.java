package in.bhargavrao.stackoverflow.natobot.validators;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;

/**
 * Created by bhargav.h on 11-Sep-16.
 */
public class AllowAllNewValidator implements Validator {

    @Override
    public boolean validate(NatoPost natoPost) {
        return natoPost.getAnswerCreationDate().isBefore(natoPost.getQuestionCreationDate().plusSeconds(2592000));
    }

}
