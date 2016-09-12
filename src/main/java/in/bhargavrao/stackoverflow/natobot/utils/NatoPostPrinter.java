package in.bhargavrao.stackoverflow.natobot.utils;

import in.bhargavrao.stackoverflow.natobot.entities.NatoPost;

/**
 * Created by bhargav.h on 12-Sep-16.
 */
public class NatoPostPrinter {
    private String printStr;
    private NatoPost natoPost;

    public NatoPostPrinter(NatoPost natoPost){
        this.printStr = PrintUtils.printDescription();
        this.natoPost = natoPost;
    }

    public NatoPostPrinter addBody(){
        this.printStr+=" Body: "+natoPost.getBodyMarkdown();
        return this;
    }

    public NatoPostPrinter addReputation(){
        this.printStr+=" Rep: "+natoPost.getReputation();
        return this;
    }

    public NatoPostPrinter addQuestionDetails(){
        this.printStr+= " [Question](//stackoverflow.com/q/"+natoPost.getQuestionID()+")";
        return this;
    }

    public NatoPostPrinter addMainTag(){
        this.printStr+= " [tag:" + natoPost.getMainTag() + "]";
        return this;
    }

    public NatoPostPrinter addDescription(){
        this.printStr+= " ["+natoPost.getTitle()+"](//stackoverflow.com/a/"+natoPost.getAnswerID()+")";
        return this;
    }

    public NatoPostPrinter addUserDetails(){
        this.printStr+= " By: ["+natoPost.getUserName()+"](//stackoverflow.com/u/"+natoPost.getUserID()+")";
        return this;
    }


    public String print(){
        return printStr;
    }
}
