package tado.web;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

public class CSSFeedbackPanel extends FeedbackPanel {

    private static final long serialVersionUID = 243457247969120536L;

    public CSSFeedbackPanel(String id) {
        super(id);
    }

    @Override
    protected Component newMessageDisplayComponent(final String id,
            final FeedbackMessage message) {
        final Component cssclass = super.newMessageDisplayComponent(id, message);
 
        String result = "";
        switch(message.getLevelAsString()) {
        case "UNDEFINED":
            result = "alert-info";
            break;
        case "DEBUG":
            result = "alert-info";
            break;
        case "INFO":
            result = "alert-info";
            break;
        case "WARNING":
            result = "alert-warning";
            break;
        case "ERROR":
            result = "alert-danger";
            break;
        case "FATAL":
            result = "alert-danger";
            break;
        }
        cssclass
                .add(new AttributeAppender("class", new Model<String>(result), " "));
        return cssclass;
    }

}