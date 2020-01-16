package trials;

import javax.net.ssl.HttpsURLConnection;

public class JiraIssue {
    JiraIssue issue;
    public JiraIssue(){
        issue = new JiraIssue();
    }

    public JiraIssue withProject(String projectName){

        //GET
        final HttpsURLConnection connect =
                new (HttpsURLConnection) URL("")
    }
}
