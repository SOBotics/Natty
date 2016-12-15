package in.bhargavrao.stackoverflow.natobot.utils;

import com.google.common.base.Optional;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.i18n.LdLocale;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfile;
import com.optimaize.langdetect.profiles.LanguageProfileReader;
import com.optimaize.langdetect.text.CommonTextObjectFactories;
import com.optimaize.langdetect.text.TextObject;
import com.optimaize.langdetect.text.TextObjectFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.tika.langdetect.OptimaizeLangDetector;

import org.apache.tika.language.detect.LanguageResult;
import org.apache.tika.language.detect.LanguageWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class CheckUtils {

	public static boolean checkIfBodyContainsKeyword(NatoPost natoPost, String keyword){
        String spareBody = stripBody(natoPost);
		return spareBody.toLowerCase().contains(keyword.toLowerCase());
	}

	public static String checkIfBodyStartsWithMention(NatoPost natoPost){
        String firstLine = natoPost.getBodyMarkdown().split("\n")[0];
        if(firstLine.startsWith("@") && firstLine.split(" ")[0].trim().matches("[@A-Za-z0-9]+")){
            return firstLine.split(" ")[0].trim();
        }
        return null;
    }

    private static String stripBody(NatoPost natoPost) {
        String body = natoPost.getBody();
        Document doc = Jsoup.parse("<body>"+body+"</body>");
        doc.getElementsByTag("a").remove();
        doc.getElementsByTag("code").remove();
        doc.getElementsByTag("img").remove();
        doc.getElementsByTag("pre").remove();
        doc.getElementsByTag("blockquote").remove();
        return doc.outerHtml();
    }

    private static String stripTags(String html) {
        Document doc = Jsoup.parse(html);
        return doc.text();
    }



    private static String stripBodyMarkdown(NatoPost natoPost){
        // TO DO
        return natoPost.getBodyMarkdown();
    }


    private static String checkForListedWords(NatoPost natoPost, String file){
        try {
            List<String> lines = Files.readAllLines(Paths.get(file));
            for(String s: lines){
                if(checkIfBodyContainsKeyword(natoPost,s.trim()))
                    return s.trim();
            }
        }
        catch (IOException exception){
            System.out.println(file+" not found.");
        }
        return null;
    }

    public static String checkIfNonEnglish(NatoPost natoPost){


        List<LanguageProfile> languageProfiles;
        com.optimaize.langdetect.LanguageDetector optimaizeDetector;
        org.apache.tika.language.detect.LanguageDetector tikaDetector;
        TextObjectFactory textObjectFactory;



        String dataToCheck = stripTags(stripBody(natoPost)).replaceAll("\\p{Punct}+", "");



        try {

            languageProfiles = new LanguageProfileReader().readAllBuiltIn();
            optimaizeDetector = LanguageDetectorBuilder.create(NgramExtractors.standard())
                    .withProfiles(languageProfiles)
                    .build();
            textObjectFactory = CommonTextObjectFactories.forDetectingOnLargeText();
            TextObject textObject = textObjectFactory.forText(dataToCheck);
            Optional<LdLocale> lang = optimaizeDetector.detect(textObject);
            if (!lang.isPresent()) {
                if(dataToCheck.length()>50) {
                   tikaDetector = new OptimaizeLangDetector().loadModels();
                   LanguageWriter writer = new LanguageWriter(tikaDetector);
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
                if(checkIfCodeBlock(natoPost)){
                    return "Gibberish";
                }
                return null;
            }
            return lang.get().getLanguage();
        }
        catch (IOException e){
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            try{
                FileUtils.appendToFile(FilePathUtils.outputErrorLogFile,sw.toString());
            }
            catch (IOException e2){
                System.out.println("File not found");
            }
        }
        return null;
    }

	private static boolean checkIfWordIsListed(String file, String word){
        try {
            return FileUtils.checkIfInFile(file,word);
        }
        catch (IOException exception){
            System.out.println(file+" not found.");
        }
        return false;
    }


	public static String checkForBlackListedWords(NatoPost natoPost){
        return checkForListedWords(natoPost, FilePathUtils.blacklistFile);
    }
	public static String checkForWhiteListedWords(NatoPost natoPost){
        return checkForListedWords(natoPost, FilePathUtils.whitelistFile);
    }
	public static boolean checkIfBlackListed(String word){
        return checkIfWordIsListed(FilePathUtils.blacklistFile,word);
    }
	public static boolean checkIfWhiteListed(String word){
        return checkIfWordIsListed(FilePathUtils.whitelistFile,word);
    }
	public static String checkForSalutation(NatoPost natoPost){
        try {
            List<String> keywords = Files.readAllLines(Paths.get(FilePathUtils.salutationsFile));
            String [] lines = natoPost.getBodyMarkdown().split("\n");
            int len = lines.length;
            if(len>3){
                lines = Arrays.copyOfRange(lines,len-3,len);
            }
            for(String line:lines){
                for (String word:keywords){
                    if(line.toLowerCase().contains(word.toLowerCase())){
                        return word;
                    }
                }
            }
        }
        catch (IOException exception){
            System.out.println("Salutations File not found.");
        }
        return null;
    }

    public static String checkForLongWords(NatoPost natoPost){
        String bodyParts[] = natoPost.getBodyMarkdown().replaceAll("[^a-zA-Z ]", " ").split(" ");
        for(String part:bodyParts){
            if (part.length()>50){
                return part;
            }
        }
        return null;
    }

	public static boolean checkIfBodyContainsQm(NatoPost natoPost){
		return checkIfBodyContainsKeyword(natoPost,"?");
	}
	public static boolean checkIfLinkOnlyAnswer(NatoPost natoPost, int threshold){
	    String body = natoPost.getBody();
        Document doc = Jsoup.parse("<body>"+body+"</body>");
        int totalLength = doc.getElementsByTag("body").first().text().length();
        int linkLength = doc.getElementsByTag("a").stream().mapToInt(x -> x.text().length()).sum();
        return ((linkLength*100)/totalLength)>threshold;
    }
    public static boolean checkIfOneLine(NatoPost natoPost){
        return !natoPost.getBodyMarkdown().trim().contains("\n");
    }
    public static boolean checkIfUnregistered(NatoPost natoPost){
        return natoPost.getAnswerer().getUserType().equals("unregistered");
    }
    public static boolean checkIfCodeBlock(NatoPost natoPost){
        return (!natoPost.getBody().contains("<code>"));
    }
    public static boolean checkIfEndsWithQm(NatoPost natoPost){
        return natoPost.getBodyMarkdown().trim().endsWith("?");
    }
    public static boolean checkIfUserIsBlacklisted(long userId){
        try {
            return FileUtils.checkIfInFile(FilePathUtils.blacklistedUsers, Long.toString(userId));
        }
        catch (IOException e){
            System.out.println("File not found");
            return false;
        }
    }
    public static boolean checkIfUnformatted(NatoPost natoPost){
        String strippedBody = stripTags(stripBody(natoPost));
        strippedBody = strippedBody.replace("\n","");
        long totLength = strippedBody.length();
        long whitespaceCount = strippedBody.chars().filter(c -> c == ' ').count();
        long alphaCount = strippedBody.chars().filter(c -> c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9').count();
        long puncCount = totLength-whitespaceCount-alphaCount;
        if (totLength!=0 && alphaCount!=0)
            return 100 * whitespaceCount/ totLength< 80 && 100 * puncCount/ alphaCount>125;
        return false;
    }
}
