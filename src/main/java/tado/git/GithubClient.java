package tado.git;

import static tado.error.GithubClientException.CAUSE.INVALID_CREDENTIAL;
import static tado.error.GithubClientException.CAUSE.MISSING_CREDENTIAL;
import static tado.error.GithubClientException.CAUSE.NO_PULL_ACCESS;
import static tado.error.GithubClientException.CAUSE.SERVER_ERROR;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import tado.error.GithubClientException;
import tado.git.Issue.IssueBuilder;

public class GithubClient implements IClient {
    
    private final GitHub github;
    private final String repo;

    public GithubClient(String path, String repo) {
        try {
            this.github = GitHubBuilder.fromPropertyFile(path).build();
        } catch (IOException e) {
            throw new GithubClientException(MISSING_CREDENTIAL);
        }
        this.repo = repo;
    }

    public GithubClient(GitHub gitHub, String repo) {
        this.github = gitHub;
        this.repo = repo;
    }

    @Override
    public void createIssue(Issue issue) {
        try {
            if (!github.isCredentialValid())
                throw new GithubClientException(INVALID_CREDENTIAL);
            GHRepository repository = github.getRepository(repo);
            if (!repository.hasPullAccess())
                throw new GithubClientException(NO_PULL_ACCESS);
            repository.createIssue(issue.title).body(issue.body).create();
        } catch (IOException e) {
            throw new GithubClientException(SERVER_ERROR);
        }
    }

    @Override
    public List<Issue> issues() {
        List<Issue> issues = new ArrayList<>();
        try {
            GHRepository repository = github.getRepository(repo);
            List<GHIssue> list = repository.getIssues(GHIssueState.ALL);
            issues.addAll(list
                .stream()
                .map(ghIssue -> IssueBuilder.get().setTitle(ghIssue.getTitle()).setBody(ghIssue.getBody()).build())
                .collect(Collectors.toList()));
        } catch (IOException e) {
            throw new GithubClientException(SERVER_ERROR);
        }
        return issues;
    }

}
