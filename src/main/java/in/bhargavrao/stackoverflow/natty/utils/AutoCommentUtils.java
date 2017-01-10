package in.bhargavrao.stackoverflow.natty.utils;

import in.bhargavrao.stackoverflow.natty.entities.AutoCommentType;
import in.bhargavrao.stackoverflow.natty.entities.PostReport;
import in.bhargavrao.stackoverflow.natty.entities.autocomments.*;

public class AutoCommentUtils {
	
	/**
	 * Checks the report and recommends a good comment for the post. 
	 * @param report The PostReport to check
	 * @return The comment that could be posted
	 */
	public static AutoComment commentForPostReport(PostReport report) {		
		Boolean isPossibleLinkOnly = false;
        Boolean hasNoCodeblock = false;
        Boolean containsBlacklistedWord = false;
        Boolean containsQM = false;
        
        Boolean containsVeryLongWord = false;
        Boolean isNonEnglish = false;
        
        for (String filter : report.getCaughtFor()) {
        	if (filter.startsWith("No Code Block")) hasNoCodeblock = true;
        	if (filter.startsWith("Possible Link Only")) isPossibleLinkOnly = true;
            if (filter.startsWith("Contains Blacklisted Word")) containsBlacklistedWord = true;  
            if (filter.startsWith("Contains Very Long Word")) containsVeryLongWord = true;
            if (filter.startsWith("Non English Post")) isNonEnglish = true;
            if (filter.startsWith("Contains ?")) containsQM = true;
        }
        
        
        //decide, which comment to use
        
        //gibberish?
        if (containsVeryLongWord && isNonEnglish) {
        	return null;
        }
        
        if (hasNoCodeblock && isPossibleLinkOnly && !containsBlacklistedWord && !containsQM) {
        	//link-only
        	System.out.println("link-only");
        	return new AutoCommentLinkOnly();
        } else {
        	System.out.println("Some NAA");
        	//check the reputation to provide different instructions for users that can't comment yet
        	if (report.getPost().getAnswerer().getReputation() < 50) {
        		//not enough rep to comment
        		System.out.println("low rep");
        		return new AutoCommentNAALowRep();
        	} else {
        		System.out.println("high rep");
        		return new AutoCommentNAAHighRep();
        	}
        }
	}
}
