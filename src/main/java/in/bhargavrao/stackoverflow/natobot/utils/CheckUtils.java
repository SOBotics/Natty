package in.bhargavrao.stackoverflow.natobot.utils;

import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class CheckUtils {

    private static String blacklistFile = ".\\src\\main\\resources\\lib\\BlackListedWords.txt";
    private static String whitelistFile = ".\\src\\main\\resources\\lib\\WhiteListedWords.txt";
    private static String salutationsFile = ".\\src\\main\\resources\\lib\\Salutations.txt";

	public static boolean checkIfBodyContainsKeyword(NatoPost natoPost, String keyword){
		String body = natoPost.getBody();	
		Document doc = Jsoup.parse("<body>"+body+"</body>");
		doc.getElementsByTag("a").remove();
		doc.getElementsByTag("code").remove();
		String spareBody = doc.outerHtml();			
		return spareBody.toLowerCase().contains(keyword.toLowerCase());
	}
	public static boolean checkForListedWords(NatoPost natoPost, String file){
	    boolean check = false;
        try {
            List<String> lines = Files.readAllLines(Paths.get(file));
            for(String s: lines){
                check = check || checkIfBodyContainsKeyword(natoPost,s.trim());
            }
        }
        catch (IOException exception){
            System.out.println(file+" not found.");
        }
        return check;
    }
	public static boolean checkIfWordIsListed(String file, String word){
        try {
            return FileUtils.checkIfInFile(file,word);
        }
        catch (IOException exception){
            System.out.println(file+" not found.");
        }
        return false;
    }
	public static boolean checkForBlackListedWords(NatoPost natoPost){
        return checkForListedWords(natoPost,blacklistFile);
    }
	public static boolean checkForWhiteListedWords(NatoPost natoPost){
        return checkForListedWords(natoPost,whitelistFile);
    }
	public static boolean checkIfBlackListed(String word){
        return checkIfWordIsListed(blacklistFile,word);
    }
	public static boolean checkIfWhiteListed(String word){
        return checkIfWordIsListed(whitelistFile,word);
    }
	public static boolean checkForSalutation(NatoPost natoPost){
	    boolean check = false;
        try {
            List<String> keywords = Files.readAllLines(Paths.get(salutationsFile));
            String [] lines = natoPost.getBodyMarkdown().split("\n");
            int len = lines.length;
            if(len>3){
                lines = Arrays.copyOfRange(lines,len-3,len);
            }
            for(String line:lines){
                for (String word:keywords){
                    if(line.toLowerCase().contains(word.toLowerCase())){
                        return true;
                    }
                }
            }
            return false;
        }
        catch (IOException exception){
            System.out.println("Salutations File not found.");
        }
        return check;
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
