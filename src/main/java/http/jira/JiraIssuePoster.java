package http.jira;

import http.HttpRequestComposer;
import infra.ConfigurationManager;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JiraIssuePoster {

    public final static class JiraIssue {
        public final String key;
        public final int apiId;

        public JiraIssue(String k, int i) {
            key = k;
            apiId = i;
        }
    }


    private String description = null;
    private int projectId = -1;
    private String summary = null;
    private int issueTypeId = -1;
    private int priorityId = 5;
    private List<String> labels = new ArrayList<>();
    private List<String> errors = new ArrayList<>();


    public JiraIssuePoster forProject(String projectKey) {


        return this;
    }

    public JiraIssuePoster withPriority(String priorityFriendly) {


        return this;
    }

    public JiraIssuePoster summary(String summary) {
        if (summary != null && !summary.trim().isEmpty())
            this.summary = summary;
        return this;
    }

    public JiraIssuePoster withDescription(String description) {
        if (description != null && !description.trim().isEmpty())
            this.description = description;
        return this;
    }

    public JiraIssuePoster ofType(String issueTypeFriendly) {


        return this;
    }

    public JiraIssuePoster label(String label) {
        labels.add(label);
        return this;
    }

    public JiraIssuePoster labels(Collection<String> labels) {
        this.labels.addAll(labels);
        return this;
    }

    public JiraIssue create() throws IOException {
        JiraIssue result = null;
        if (projectId == -1) errors.add("Project is not set. Use withProject().");
        if (issueTypeId == -1) errors.add("Issue type is not set. Use ofType().");
        if (summary == null)
            errors.add("Summary is either not set or only consists of whitespaces. Use summary().");
        if (errors.size() > 0) {
            throw new IllegalStateException("Cannot create an issue:\n" + String.join("-n", errors));
        }

        JSONArray labels = new JSONArray();
        this.labels.stream().distinct().forEach(labels::put);

        JSONObject payload = new JSONObject();
        JSONObject fields = new JSONObject();
        fields.put("project", new JSONObject().put("id", projectId));
        fields.put("summary", summary);
        fields.put("description", description);
        fields.put("reporter", new JSONObject().put("name", ConfigurationManager.getInstance().getConfig("jiraUser").asString()));
        fields.put("issuetype", new JSONObject().put("id", issueTypeId));
        fields.put("priority", new JSONObject().put("id", priorityId));
        fields.put("labels", labels);
        payload.put("fields", fields);

        final String creds = String.format("%s:%s",
                ConfigurationManager.getInstance().getConfig("jiraUser").asString(),
                ConfigurationManager.getInstance().getConfig("jiraPwd").asString()
        );

        final HttpRequestComposer.HttpResponse response = new HttpRequestComposer()
                .auth(HttpRequestComposer.AuthType.BASIC, new Base64().encodeToString(creds.getBytes()))
                .via(HttpRequestComposer.HTTPMethod.POST)
                .withContentType(HttpRequestComposer.ContentType.JSON)
                .payload(payload.toString())
                // надо бы наловчиться получать это куки прямо как взаправду.... но мне лень =)
                .setHeader("Cookie", "JSESSIONID=8FCCF738DF94666423D6FC3AD3DB6C4A")
                .fire("https://jira.hillel.it/rest/api/2/issue");

if(response.rescode == 201){
    JSONObject responseParsed = new JSONObject(response.resBody);
    result = new JiraIssue(responseParsed.getString("key"), responseParsed.getInt("id"));

}


        return result;
    }
}
