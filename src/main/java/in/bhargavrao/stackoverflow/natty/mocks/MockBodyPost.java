package in.bhargavrao.stackoverflow.natty.mocks;


import in.bhargavrao.stackoverflow.natty.model.Post;
import in.bhargavrao.stackoverflow.natty.model.SOUser;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.time.Instant;

/**
 * Created by bhargav.h on 27-Aug-17.
 */
public class MockBodyPost extends Post{

    public MockBodyPost(String bodyMarkdown) {

        super("","",null,null,
                1, 1, "", bodyMarkdown, null, null, null, "", "");
        SOUser asker = new SOUser("testUser",-1,1,"registered");
        SOUser answerer = new SOUser("testUser",-1,1,"unregistered");

        setAnswerCreationDate(Instant.now());
        setQuestionCreationDate(Instant.EPOCH);
        setAsker(asker);
        setAnswerer(answerer);

        Parser parser = Parser.builder().build();
        Node document = parser.parse(bodyMarkdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        setBody(renderer.render(document));

    }
}
