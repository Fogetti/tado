package tado.error;

public class GithubClientException extends RuntimeException {

    private static final long serialVersionUID = 680076527477665676L;
    
    public enum CAUSE {
        MISSING_CREDENTIAL,
        INVALID_CREDENTIAL,
        EMPTY_TITLE,
        EMPTY_BODY,
        NO_PULL_ACCESS,
        UNKNOWN_ERROR
    }
    
    private final CAUSE cause;
    
    public GithubClientException(CAUSE cause) {
        this.cause = cause;
    }
    
    public CAUSE getCauseEnum() {
        return cause;
    }
}