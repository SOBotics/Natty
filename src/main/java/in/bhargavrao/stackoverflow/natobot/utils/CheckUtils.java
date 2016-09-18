package in.bhargavrao.stackoverflow.natobot.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;
import org.jsoup.select.Elements;

public class CheckUtils {

	public static boolean checkIfBodyContainsQm(NatoPost natoPost){
		String body = natoPost.getBody();	
		Document doc = Jsoup.parse("<body>"+body+"</body>");
		doc.getElementsByTag("a").remove();
		doc.getElementsByTag("code").remove();
		String spareBody = doc.outerHtml();			
		return spareBody.contains("?");
	}
	public static boolean checkIfLinkOnlyAnswer(NatoPost natoPost, int threshold){
	    String body = natoPost.getBody();
        Document doc = Jsoup.parse("<body>"+body+"</body>");
        int totalLength = doc.getElementsByTag("body").first().text().length();
        int linkLength = doc.getElementsByTag("a").stream().mapToInt(x -> x.text().length()).sum();
        return ((linkLength*100)/totalLength)>threshold;
    }
}
