package tado.git;

import static org.junit.Assert.assertTrue;
import static tado.error.GithubClientException.CAUSE.EMPTY_BODY;
import static tado.error.GithubClientException.CAUSE.EMPTY_TITLE;

import org.junit.Test;

import tado.error.GithubClientException;
import tado.git.Issue.IssueBuilder;

public class IssueTest {

    @Test
    public void missingTitle() throws Exception {
        // Given title is missing
        IssueBuilder builder = IssueBuilder.get().setBody("body");
        
        // When the user is trying to create an issue
        try {
            builder.build();
        } catch (GithubClientException e) {
            // Then it fails
            assertTrue(e.getCauseEnum().equals(EMPTY_TITLE));    
        }
    }
    
    @Test
    public void missingBody() throws Exception {
        // Given body is missing
        IssueBuilder builder = IssueBuilder.get().setTitle("title");
        
        // When the user is trying to create an issue
        try {
            builder.build();
        } catch (GithubClientException e) {
            // Then it fails
            assertTrue(e.getCauseEnum().equals(EMPTY_BODY));    
        }
    }
    
}