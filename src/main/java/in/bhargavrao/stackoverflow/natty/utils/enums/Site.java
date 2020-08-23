package in.bhargavrao.stackoverflow.natty.utils.enums;

public enum Site {
    STACK_OVERFLOW("stackoverflow", "stackoverflow.com"),
    ASK_UBUNTU("askubuntu", "askubuntu.com");

    private final String siteName;
    private final String siteUrl;

    private Site(String siteName, String siteUrl){
        this.siteName = siteName;
        this.siteUrl = siteUrl;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

}
