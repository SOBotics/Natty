package in.bhargavrao.stackoverflow.natty.services;

import com.google.common.base.Optional;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.i18n.LdLocale;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfile;
import com.optimaize.langdetect.profiles.LanguageProfileReader;
import com.optimaize.langdetect.text.CommonTextObjectFactories;
import com.optimaize.langdetect.text.TextObject;
import com.optimaize.langdetect.text.TextObjectFactory;
import in.bhargavrao.stackoverflow.natty.model.Post;
import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.apache.tika.language.detect.LanguageWriter;

import java.io.IOException;
import java.util.List;

import static in.bhargavrao.stackoverflow.natty.utils.CheckUtils.checkIfNoCodeBlock;
import static in.bhargavrao.stackoverflow.natty.utils.CheckUtils.stripBody;
import static in.bhargavrao.stackoverflow.natty.utils.CheckUtils.stripTags;

public class NonEnglishCheckerService implements CheckerService<String> {

    private List<LanguageProfile> languageProfiles;
    private com.optimaize.langdetect.LanguageDetector optimaizeDetector;
    private org.apache.tika.language.detect.LanguageDetector tikaDetector;
    private TextObjectFactory textObjectFactory;
    private LanguageWriter writer;

    public NonEnglishCheckerService() {
        try {
            languageProfiles = new LanguageProfileReader().readAllBuiltIn();
            optimaizeDetector = LanguageDetectorBuilder.create(NgramExtractors.standard())
                    .withProfiles(languageProfiles)
                    .build();
            textObjectFactory = CommonTextObjectFactories.forDetectingOnLargeText();
            tikaDetector = new OptimaizeLangDetector().loadModels();
            writer = new LanguageWriter(tikaDetector);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String check(Post post) {

        String dataToCheck = stripTags(stripBody(post)).replaceAll("\\p{Punct}+", "");
        try {
            TextObject textObject = textObjectFactory.forText(dataToCheck);
            Optional<LdLocale> lang = optimaizeDetector.detect(textObject);
            if (!lang.isPresent()) {
                if(dataToCheck.length()>50) {
                    writer.append(dataToCheck);
                    LanguageResult result = writer.getLanguage();
                    String tikaLang = result.getLanguage();
                    writer.close();

                    if (!tikaLang.toLowerCase().equals("")) {
                        return tikaLang;
                    }
                    else{
                        return null;
                    }
                }
                else if(dataToCheck.length()<50){
                    return null;
                }
                if(checkIfNoCodeBlock(post)){
                    return "Gibberish";
                }
                return null;
            }
            return lang.get().getLanguage();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
