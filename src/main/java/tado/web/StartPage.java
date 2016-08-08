package tado.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.file.File;
import org.kohsuke.github.GitHubBuilder;

import tado.git.GithubClient;
import tado.git.IClient;

public class StartPage extends WebPage {

    private static final long serialVersionUID = 6626700998437241250L;

    public StartPage(final PageParameters parameters)
    {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        
        setFeedbackPanel();
        if (get("form") == null) {
            setLoginForm("form", buildGithubClient());
        }
    }
    
    private IClient buildGithubClient() {
        GithubClient client;
        try {
            Properties prop = loadProps();
            client = new GithubClient(GitHubBuilder.fromCredentials().build(), prop.getProperty("repo"));
        } catch (IOException e) {
            error("Could not find the github credentials or the repository name.");
            return null;
        }
        return client;
    }

    private Properties loadProps() throws IOException {
        Properties prop = new Properties();
        prop.load(Files.newBufferedReader(Paths.get(System.getProperty("user.home")+File.separator+".gitrepo")));
        return prop;
    }

    void setFeedbackPanel() {
        final FeedbackPanel feedback = new CSSFeedbackPanel("feedback");
        add(feedback);
    }
    
    void setLoginForm(String componentId, IClient client) {
        GithubIssueForm form = new GithubIssueForm(componentId, client);
        form.setVisible(client == null ? false : true);
        add(form);
    }
}