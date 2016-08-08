package tado.web;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

public class App extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return StartPage.class;
    }
    
    @Override
    public final Session newSession(Request request, Response response) {
        return new TadoSession(request);
    }

    @Override
    public void init()
    {
        super.init();
        getResourceSettings().setThrowExceptionOnMissingResource(true);
        mountPage("/", StartPage.class);
    }

}
