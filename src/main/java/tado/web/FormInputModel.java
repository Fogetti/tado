package tado.web;

import java.io.Serializable;

public class FormInputModel implements Serializable {

    private static final long serialVersionUID = -5966326253594304702L;
    
    private String title = "Title";
    private String body = "Body";
    
    public String getTitle() {
        return title;
    }
    public String getBody() {
        return body;
    }
    
}