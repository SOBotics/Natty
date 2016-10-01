package in.bhargavrao.stackoverflow.natobot.validators;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;

import java.util.Arrays;

/**
 * Created by bhargav.h on 11-Sep-16.
 */
public class AllowOnlyTagValidator implements Validator {

    private String tag;

    public AllowOnlyTagValidator(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean validate(NatoPost natoPost) {
        return Arrays.asList(natoPost.getTags()).contains(tag);
    }

}
