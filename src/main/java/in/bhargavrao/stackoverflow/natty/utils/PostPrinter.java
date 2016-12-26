package in.bhargavrao.stackoverflow.natty.utils;

import in.bhargavrao.stackoverflow.natty.entities.Post;

/**
 * Created by bhargav.h on 12-Sep-16.
 */
public class PostPrinter {
    private String printStr;
    private Post post;

    public PostPrinter(Post post){
        this.printStr = PrintUtils.printDescription();
        this.post = post;
    }
    public PostPrinter(Post post, String description){
        this.printStr = description;
        this.post = post;
    }

    public PostPrinter addFirstLine(){
        String firstline = post.getBodyMarkdown().split("\n")[0].trim();
        if(firstline.length()>200) {
            firstline = firstline.substring(0, 200);
            if(firstline.contains(" ")) {
                firstline = firstline.substring(0, firstline.lastIndexOf(' '));
            }
        }
        this.printStr+=" **Body Starts With:** "+firstline.replace("[","\\[");
        return this;
    }

    public PostPrinter addBodyLength(){
        this.printStr+=" **BodyLength:** "+ post.getBodyMarkdown().length();
        return this;
    }

    public PostPrinter addBody(){
        this.printStr+=" **Body:** "+ post.getBodyMarkdown();
        return this;
    }

    public PostPrinter addReputation(){
        this.printStr+=" **Rep:** "+ post.getAnswerer().getReputation();
        return this;
    }

    public PostPrinter addQuestionDetails(){
        this.printStr+= " [Question](//stackoverflow.com/q/"+ post.getQuestionID()+")";
        return this;
    }

    public PostPrinter addMainTag(){
        this.printStr+= " [tag:" + post.getMainTag() + "]";
        return this;
    }

    public PostPrinter addDescription(){
        this.printStr+= " ["+ post.getTitle()+"](//stackoverflow.com/a/"+ post.getAnswerID()+")";
        return this;
    }

    public PostPrinter addQuesionLink(){
        this.printStr+= " [Link to Post](//stackoverflow.com/a/"+ post.getAnswerID()+")";
        return this;
    }

    public PostPrinter addUserDetails(){
        this.printStr+= " **By:** ["+ post.getAnswerer().getUsername()+"](//stackoverflow.com/u/"+ post.getAnswerer().getUserId()+")";
        return this;
    }

    public PostPrinter addMessage(String message){
        this.printStr+= message;
        return this;
    }

    public String print(){
        return printStr;
    }
}
