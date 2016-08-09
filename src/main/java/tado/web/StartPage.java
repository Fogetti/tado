package tado.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.file.File;
import org.kohsuke.github.GitHubBuilder;

import tado.git.GithubClient;
import tado.git.IClient;
import tado.git.Issue;

public class StartPage extends WebPage {

    private class ListModel extends LoadableDetachableModel<List<Issue>> {
        private static final long serialVersionUID = 6446479016880681170L;

        @Override
        protected List<Issue> load()
        {
            return buildGithubClient().issues();
        }
    }

    private static final long serialVersionUID = 6626700998437241250L;

    public StartPage(final PageParameters parameters)
    {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        IClient client = buildGithubClient();
        setFeedbackPanel();

        LoadableDetachableModel<List<Issue>> issuesModel = new ListModel();

        add(new ListView<Issue>("issues", issuesModel) {
            private static final long serialVersionUID = -1458753295331475582L;

            @Override
            protected void populateItem(ListItem<Issue> item) {
                IModel<Issue> model = item.getModel();
                item.add(new Label("title", new PropertyModel<>(model.getObject(), "title")));
                item.add(new Label("body", new PropertyModel<>(model.getObject(), "body")));
            }
        });

        if (get("form") == null) {
            setLoginForm("form", client);
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

    private void setFeedbackPanel() {
        final FeedbackPanel feedback = new CSSFeedbackPanel("feedback");
        add(feedback);
    }

    private void setLoginForm(String componentId, IClient client) {
        GithubIssueForm form = new GithubIssueForm(componentId, client);
        form.setVisible(client == null ? false : true);
        add(form);
    }
}