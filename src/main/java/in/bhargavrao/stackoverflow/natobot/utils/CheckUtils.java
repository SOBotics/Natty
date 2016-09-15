package in.bhargavrao.stackoverflow.natobot.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;

public class CheckUtils {

	public static boolean checkIfBodyContainsQm(NatoPost natoPost){
		String body = natoPost.getBody();	
		body = "<body>"+body+"</body>";
		Document doc = Jsoup.parse(body);		
		doc.getElementsByTag("a").remove();
		doc.getElementsByTag("code").remove();
		String spareBody = doc.outerHtml();			
		return spareBody.contains("?");
	}
}
