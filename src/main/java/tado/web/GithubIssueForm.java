package tado.web;

import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import tado.error.GithubClientException;
import tado.git.IClient;
import tado.git.Issue;
import tado.git.Issue.IssueBuilder;

public class GithubIssueForm extends StatelessForm<FormInputModel> {

    private static final long serialVersionUID = 3392168020298341645L;
    private IClient client;
    
    public GithubIssueForm(String id, IClient client) {
        super(id, new CompoundPropertyModel<>(new FormInputModel()));
        this.client = client;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new TextField<String>("title").setRequired(true).setLabel(new Model<>("String")));
        add(new TextArea<String>("body").setRequired(true).setLabel(new Model<>("String")));
        add(new CreateButton("createButton"));
    }

    /**
     * @see org.apache.wicket.markup.html.form.Form#onSubmit()
     */
    @Override
    public void onSubmit()
    {
        FormInputModel model = (FormInputModel) getDefaultModelObject();
        Issue issue = IssueBuilder.get().setTitle(model.getTitle()).setBody(model.getBody()).build();
        try {
            client.createIssue(issue);
            System.out.println("Saved a new issue: " + issue);
            info("Saved a new issue " + issue);
        } catch(GithubClientException e) {
            error("Could not save the issue. "+e.getCauseEnum());
        }
    }
}