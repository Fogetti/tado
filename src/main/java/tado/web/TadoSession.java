package tado.web;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class TadoSession extends WebSession {

    public TadoSession(Request request) {
        super(request);
    }

    private static final long serialVersionUID = -1520266102835213525L;

}