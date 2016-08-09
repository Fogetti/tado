package tado.git;

import java.util.List;

public interface IClient {

    void createIssue(Issue issue);
    
    List<Issue> issues();
}