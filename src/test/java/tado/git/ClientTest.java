package tado.git;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tado.error.GithubClientException.CAUSE.INVALID_CREDENTIAL;
import static tado.error.GithubClientException.CAUSE.MISSING_CREDENTIAL;
import static tado.error.GithubClientException.CAUSE.NO_PULL_ACCESS;

import org.junit.Before;
import org.junit.Test;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueBuilder;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import tado.error.GithubClientException;
import tado.git.Issue.IssueBuilder;

public class ClientTest {

    private GitHub github;
    private IClient client;
    private String repo;
    private Issue issue;
    private GHRepository repository;
    private String title;
    private String body;

    @Before
    public void setup() throws Exception {
        title = "title";
        body = "body";
        repo = "repo";
        github = mock(GitHub.class);
        when(github.isCredentialValid()).thenReturn(true);
        repository = mock(GHRepository.class);
        when(github.getRepository(repo)).thenReturn(repository);
        client = new GithubClient(github, repo);
        issue = IssueBuilder.get().setTitle(title).setBody(body).build();
    }

    @Test
    public void credentialMissing() throws Exception {
        // Given the credentials are missing
        try {
            client = new GithubClient("invalid-path", repo);
            fail();
        } catch(GithubClientException e) {
            // Then the client throws exception
            assertTrue(e.getCauseEnum().equals(MISSING_CREDENTIAL));
        }
    }
    
    @Test
    public void credentialInvalid() throws Exception {
        // Given the frontend sends title + body
        
        // When the credentials are invalid
        when(github.isCredentialValid()).thenReturn(false);
        
        try {
            client.createIssue(issue);
            fail();
        } catch(GithubClientException e) {
            // Then the client throws Exception
            assertTrue(e.getCauseEnum().equals(INVALID_CREDENTIAL));
        }
    }
    
    @Test
    public void noPullAccess() throws Exception {
        // Given the frontend user has no pull access
        when(repository.hasPullAccess()).thenReturn(false);
       
        // When the client is trying to create an issue
        try {
            client.createIssue(issue);
            fail();
        } catch(GithubClientException e) {
            // Then it fails
            assertTrue(e.getCauseEnum().equals(NO_PULL_ACCESS));
        }
    }
    
    @Test
    public void createSuccessfully() throws Exception {
        // Given there are no problems
        when(repository.hasPullAccess()).thenReturn(true);
        GHIssueBuilder builder = mock(GHIssueBuilder.class);
        when(repository.createIssue(title)).thenReturn(builder);
        when(builder.body(body)).thenReturn(builder);
        GHIssue value = mock(GHIssue.class);
        when(builder.create()).thenReturn(value);
        
        // When the client is called with a valid issue
        try {
            // Then it succeeds
            client.createIssue(issue);
        } catch(GithubClientException e) {
            fail();
        }
        
        verify(builder, atLeast(1)).create();
        verify(value, atLeast(1)).close();
    }
}