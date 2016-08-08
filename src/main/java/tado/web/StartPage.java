package tado.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import tado.git.GithubClient;
import tado.git.IClient;

public class StartPage extends WebPage {

    private static final long serialVersionUID = 6626700998437241250L;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        
        IClient client = buildGithubClient();
        
        setFeedbackPanel();
        if (get("form") == null) {
            setLoginForm("form", client);
        }
    }
    
    private IClient buildGithubClient() {
        GithubClient client = new GithubClient("/Users/fogetti/Work/tado/.github", "Fogetti/tado");
        return client;
    }

    void setFeedbackPanel() {
        final FeedbackPanel feedback = new CSSFeedbackPanel("feedback");
        add(feedback);
    }
    
    void setLoginForm(String componentId, IClient client) {
        add(new GithubIssueForm(componentId, client));
    }
}