package in.bhargavrao.stackoverflow.natty.utils;

import com.google.common.base.Optional;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.i18n.LdLocale;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfile;
import com.optimaize.langdetect.profiles.LanguageProfileReader;
import com.optimaize.langdetect.text.CommonTextObjectFactories;
import com.optimaize.langdetect.text.TextObject;
import com.optimaize.langdetect.text.TextObjectFactory;
import in.bhargavrao.stackoverflow.natty.model.ListType;
import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.services.FileStorageService;
import in.bhargavrao.stackoverflow.natty.services.StorageService;
import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.apache.tika.language.detect.LanguageWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtils {

    public static String checkForBlackListedWords(Post post){
        return checkForListedWords(post, ListType.BLACKLIST);
    }
    public static String checkForWhiteListedWords(Post post){
        return checkForListedWords(post, ListType.WHITELIST);
    }


    private static String checkForListedWords(Post post, ListType type){
        StorageService service = new FileStorageService();
        List<String> lines = service.getListWords(type);
        for(String s: lines){
            if(checkIfBodyContainsKeyword(post,s.trim()))
                return s.trim();
        }
        return null;
    }

    private static boolean checkIfBodyContainsKeyword(Post post, String keyword){
        String spareBody = stripBody(post);
		return spareBody.toLowerCase().contains(keyword.toLowerCase());
	}

    public static String stripBody(Post post) {
        String body = post.getBody();
        Document doc = Jsoup.parse("<body>"+body+"</body>");
        doc.getElementsByTag("a").remove();
        doc.getElementsByTag("code").remove();
        doc.getElementsByTag("img").remove();
        doc.getElementsByTag("pre").remove();
        doc.getElementsByTag("blockquote").remove();
        return doc.outerHtml();
    }


	public static String checkIfBodyStartsWithMention(Post post){
        String firstLine = post.getBodyMarkdown().split("\n")[0];
        if(firstLine.startsWith("@") && firstLine.split(" ")[0].trim().matches("[@A-Za-z0-9]+")){
            return firstLine.split(" ")[0].trim();
        }
        return null;
    }

    public static String stripTags(String html) {
        Document doc = Jsoup.parse(html);
        return doc.text();
    }


	public static String checkForSalutation(Post post){
        StorageService service = new FileStorageService();
        List<String> keywords = service.getListWords(ListType.SALUTE);
        String [] lines = post.getBodyMarkdown().split("\n");
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
        return null;
    }

    public static String checkForLongWords(Post post){
        String bodyParts[] = post.getBodyMarkdown().replaceAll("[^a-zA-Z ]", " ").split(" ");
        for(String part:bodyParts){
            if (part.length()>50){
                return part;
            }
        }
        return null;
    }

	public static boolean checkIfBodyContainsQm(Post post){
		return checkIfBodyContainsKeyword(post,"?");
	}
	public static boolean checkIfLinkOnlyAnswer(Post post, int threshold){
	    String body = post.getBody();
        Document doc = Jsoup.parse("<body>"+body+"</body>");
        int totalLength = doc.getElementsByTag("body").first().text().length();
        int linkLength = doc.getElementsByTag("a").stream().mapToInt(x -> x.text().length()).sum();
        return ((linkLength*100)/totalLength)>threshold;
    }
    public static boolean checkIfOneLine(Post post){
        return !post.getBodyMarkdown().trim().contains("\n");
    }
    public static boolean checkIfUnregistered(Post post){
        return post.getAnswerer().getUserType().equals("unregistered");
    }
    public static boolean checkIfNoCodeBlock(Post post){
        return (!post.getBody().contains("<code>"));
    }
    public static boolean checkIfEndsWithQm(Post post){
        return post.getBodyMarkdown().trim().endsWith("?");
    }
    public static boolean checkIfUserIsBlacklisted(long userId){
        StorageService service = new FileStorageService();
        return service.checkListWord(Long.toString(userId), ListType.USER_BLACKLIST);
    }
    public static boolean checkIfUnformatted(Post post){
        String strippedBody = stripTags(stripBody(post));
        strippedBody = strippedBody.replace("\n","");
        long totLength = strippedBody.length();
        long whitespaceCount = strippedBody.chars().filter(c -> c == ' ').count();
        long alphaCount = strippedBody.chars().filter(c -> c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9').count();
        long puncCount = totLength-whitespaceCount-alphaCount;
        if (totLength!=0 && alphaCount!=0)
            return 100 * whitespaceCount/ totLength< 80 && 100 * puncCount/ alphaCount>125;
        return false;
    }

    public static boolean checkIfInteger(String str)
    {
        try{
            double d = Integer.parseInt(str);
        }
        catch(NumberFormatException e){
            return false;
        }
        return true;
    }

}
