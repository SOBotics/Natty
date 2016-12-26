package in.bhargavrao.stackoverflow.natty.validators;

import in.bhargavrao.stackoverflow.natty.entities.Post;

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
    public boolean validate(Post post) {
        return Arrays.asList(post.getTags()).contains(tag);
    }

}
