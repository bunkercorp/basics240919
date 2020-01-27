package trials;

import javax.net.ssl.HttpsURLConnection;
import java.util.List;

public class JiraIssue {
    JiraIssue issue;

    String projectId,summary,desciption,issueType;
    List<String> labels;

    public static void main(String[] args) {

    }

    public void createTicket(JiraIssue jiraIssue){
        // формируется запрос на создание готового тикета

    }

    public String withProject(String projectName){
        String projectId = new String();
//        //GET
//        request to api for getting list of all projects
        // parse response, find project id by projectname
        return  projectId;
    }
}
