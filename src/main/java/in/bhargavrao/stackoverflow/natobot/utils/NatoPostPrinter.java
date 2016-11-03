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

    public NatoPostPrinter addFirstLine(){
        String firstline = natoPost.getBodyMarkdown().split("\n")[0].trim();
        if(firstline.length()>200) {
            firstline = firstline.substring(0, 200);
            if(firstline.contains(" ")) {
                firstline = firstline.substring(0, firstline.lastIndexOf(' '));
            }
        }
        this.printStr+=" **Body Starts With:** "+firstline.replace("[","\\[");
        return this;
    }

    public NatoPostPrinter addBodyLength(){
        this.printStr+=" **BodyLength:** "+natoPost.getBodyMarkdown().length();
        return this;
    }

    public NatoPostPrinter addBody(){
        this.printStr+=" **Body:** "+natoPost.getBodyMarkdown();
        return this;
    }

    public NatoPostPrinter addReputation(){
        this.printStr+=" **Rep:** "+natoPost.getAnswerer().getReputation();
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

    public NatoPostPrinter addQuesionLink(){
        this.printStr+= " [Link to Post](//stackoverflow.com/a/"+natoPost.getAnswerID()+")";
        return this;
    }

    public NatoPostPrinter addUserDetails(){
        this.printStr+= " **By:** ["+natoPost.getAnswerer().getUsername()+"](//stackoverflow.com/u/"+natoPost.getAnswerer().getUserId()+")";
        return this;
    }

    public NatoPostPrinter addMessage(String message){
        this.printStr+= message;
        return this;
    }

    public String print(){
        return printStr;
    }
}
