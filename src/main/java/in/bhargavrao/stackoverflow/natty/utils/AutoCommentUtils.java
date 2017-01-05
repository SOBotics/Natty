package in.bhargavrao.stackoverflow.natty.utils;

import in.bhargavrao.stackoverflow.natty.entities.PostReport;

public class AutoCommentUtils {
	
	/**
	 * Checks the report and recommends a good comment for the post. 
	 * @param report The PostReport to check
	 * @return The comment that could be posted
	 */
	public static String commentForPostReport(PostReport report) {		
		Boolean isPossibleLinkOnly = false;
        Boolean hasNoCodeblock = false;
        Boolean containsBlacklistedWord = false;
        
        for(String filter: report.getCaughtFor()){
            //filters to decide which auto-comment to use
            if (filter.equalsIgnoreCase("No Code Block")) hasNoCodeblock = true;
            if (filter.equalsIgnoreCase("Possible Link Only")) isPossibleLinkOnly = true;
            if (filter.equalsIgnoreCase("Contains Blacklisted Word")) containsBlacklistedWord = true;
        }        
        
        //decide, which comment to use
        if (hasNoCodeblock && isPossibleLinkOnly && !containsBlacklistedWord) {
        	//link-only
        	System.out.println("link-only");
        	return "link-only";
        } else {
        	System.out.println("naa");
        	return "naa";
        }
	}
}
