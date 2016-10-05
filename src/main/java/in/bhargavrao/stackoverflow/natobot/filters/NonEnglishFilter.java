package in.bhargavrao.stackoverflow.natobot.filters;


import com.google.common.base.Optional;
import com.optimaize.langdetect.LanguageDetector;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.i18n.LdLocale;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfile;
import com.optimaize.langdetect.profiles.LanguageProfileReader;
import com.optimaize.langdetect.text.CommonTextObjectFactories;
import com.optimaize.langdetect.text.TextObject;
import com.optimaize.langdetect.text.TextObjectFactory;
import in.bhargavrao.stackoverflow.natobot.commands.Check;
import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import in.bhargavrao.stackoverflow.natobot.utils.CheckUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by bhargav.h on 04-Oct-16.
 */
public class NonEnglishFilter implements Filter {
    private NatoPost post;
    private double value;
    private String language;

    public NonEnglishFilter(NatoPost post) {
        this.post = post;
        value = 2;
        language = null;

    }

    @Override
    public boolean filter() {
        String language = CheckUtils.checkIfNonEnglish(post);
        if(language!=null && !language.equalsIgnoreCase("en")){
            this.language = language;
            return true;
        }
        return false;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String description() {
        return "Non English Post - "+language;
    }
}
