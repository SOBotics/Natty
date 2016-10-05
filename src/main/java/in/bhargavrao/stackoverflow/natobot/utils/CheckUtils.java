package in.bhargavrao.stackoverflow.natobot.utils;

import com.google.common.base.Optional;
import com.google.gson.JsonObject;
import com.optimaize.langdetect.LanguageDetector;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.i18n.LdLocale;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfile;
import com.optimaize.langdetect.profiles.LanguageProfileReader;
import com.optimaize.langdetect.text.CommonTextObjectFactories;
import com.optimaize.langdetect.text.TextObject;
import com.optimaize.langdetect.text.TextObjectFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import org.jsoup.select.Elements;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
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
        if(firstLine.startsWith("@")){
            return firstLine.split(" ")[0];
        }
        return null;
    }

    private static String stripBody(NatoPost natoPost) {
        String body = natoPost.getBody();
        Document doc = Jsoup.parse("<body>"+body+"</body>");
        doc.getElementsByTag("a").remove();
        doc.getElementsByTag("code").remove();
        doc.getElementsByTag("blockquote").remove();
        return doc.outerHtml();
    }

    private static String stripBodyMarkdown(NatoPost natoPost){
        String strippedBody = stripBody(natoPost);

        File xsltFile = new File("./lib/markdown.xsl");

        Source xmlSource = new StreamSource(new StringReader(strippedBody));
        Source xsltSource = new StreamSource(xsltFile);

        TransformerFactory transFact =
                TransformerFactory.newInstance();
        try {
            Transformer trans = transFact.newTransformer(xsltSource);

            StringWriter result = new StringWriter();
            trans.transform(xmlSource, new StreamResult(result));
            return result.toString();

        }
        catch (TransformerException e){
            e.printStackTrace();
        }
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
        LanguageDetector languageDetector;
        TextObjectFactory textObjectFactory;
        try {
            languageProfiles = new LanguageProfileReader().readAllBuiltIn();
            languageDetector = LanguageDetectorBuilder.create(NgramExtractors.standard())
                    .withProfiles(languageProfiles)
                    .build();
            textObjectFactory = CommonTextObjectFactories.forDetectingOnLargeText();
            TextObject textObject = textObjectFactory.forText(stripBody(natoPost).replace('<',' ').replace('>',' '));
            Optional<LdLocale> lang = languageDetector.detect(textObject);
            if (!lang.isPresent()) {
                return null;
            }
            return lang.get().getLanguage();
        }
        catch (IOException e){
            e.printStackTrace();
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
        return checkForListedWords(natoPost,ListedWordsUtils.blacklistFile);
    }
	public static String checkForWhiteListedWords(NatoPost natoPost){
        return checkForListedWords(natoPost,ListedWordsUtils.whitelistFile);
    }
	public static boolean checkIfBlackListed(String word){
        return checkIfWordIsListed(ListedWordsUtils.blacklistFile,word);
    }
	public static boolean checkIfWhiteListed(String word){
        return checkIfWordIsListed(ListedWordsUtils.whitelistFile,word);
    }
	public static String checkForSalutation(NatoPost natoPost){
        try {
            List<String> keywords = Files.readAllLines(Paths.get(ListedWordsUtils.salutationsFile));
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
        return natoPost.getUserType().equals("unregistered");
    }
    public static boolean checkIfCodeBlock(NatoPost natoPost){
        return (!natoPost.getBody().contains("<code>"));
    }
    public static boolean checkIfEndsWithQm(NatoPost natoPost){
        return natoPost.getBodyMarkdown().trim().endsWith("?");
    }
}
