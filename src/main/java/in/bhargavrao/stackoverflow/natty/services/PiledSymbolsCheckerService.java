package in.bhargavrao.stackoverflow.natty.services;

import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.utils.CheckUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PiledSymbolsCheckerService implements CheckerService<String> {

    private Pattern regex;

    public PiledSymbolsCheckerService() {
        regex = Pattern.compile("(\\?{2,}|!{2,}|!,{2,})");
    }

    /**
     * Checks if the post contains piled symbols such as "???" or "!!!"
     * @return The matched symbols; null, if the post doesn't contain piled symbols
     * */
    @Override
    public String check(Post post) {
        //RegEx to match all code-blocks: (<code>(?!:<\/code>).*|<\/code>)
        //Example: https://regex101.com/r/FEV4PJ/4

        //System.out.println(post.getBody());

        //Remove all code from the post
        //String cleanPost = post.getBody().replaceAll("(<code>(?!:<\\/code>).*|<\\/code>)", "");
        String cleanPost = CheckUtils.stripBody(post);
        //System.out.println(cleanPost);


        Matcher matcher = regex.matcher(cleanPost);
        return matcher.find() ? matcher.group(1) : null;
    }
}
