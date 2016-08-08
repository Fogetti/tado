package tado.web;

import org.apache.wicket.markup.html.WebPage;

public class StartPage extends WebPage {

    private static final long serialVersionUID = 6626700998437241250L;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        if (get("form") == null) {
            setLoginForm("form");
        }
    }

    void setLoginForm(String componentId) {
        add(new GithubIssueForm(componentId));
    }
}