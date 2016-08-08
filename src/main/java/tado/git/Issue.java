package tado.git;

import static tado.error.GithubClientException.CAUSE.EMPTY_BODY;
import static tado.error.GithubClientException.CAUSE.EMPTY_TITLE;

import org.apache.commons.lang.StringUtils;

import tado.error.GithubClientException;

public class Issue {

    public final String title;
    public final String body;
    
    private Issue(String title, String body) {
        if (StringUtils.isBlank(title)) throw new GithubClientException(EMPTY_TITLE);
        if (StringUtils.isBlank(body)) throw new GithubClientException(EMPTY_BODY);
        this.title = title;
        this.body = body;
    }
    
    public static class IssueBuilder {
        
        private String title;
        private String body;
        
        public static IssueBuilder get() {
            return new IssueBuilder();
        }

        public IssueBuilder setTitle(String title) {
            this.title = title;
            return this;
        }
        
        public IssueBuilder setBody(String body) {
            this.body = body;
            return this;
        }
        
        public Issue build() {
            return new Issue(this.title, this.body);
        }
    }
}