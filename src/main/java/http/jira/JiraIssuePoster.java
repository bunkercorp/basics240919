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
import java.util.function.Consumer;

public class JiraIssuePoster {

    public final static class JiraIssue {
        public final String key;
        public final int apiId;

        public JiraIssue(String k, int i) {
            key = k;
            apiId = i;
        }
    }

    private final static String creds = String.format("%s:%s",
            ConfigurationManager.getInstance().getConfig("jiraUser").asString(),
            ConfigurationManager.getInstance().getConfig("jiraPwd").asString()
    );


    private String description = null;
    private String projectKey = null;
    private int projectId = -1;
    private String summary = null;
    private String issueType = null;
    private int priorityId = 5; // lowest
    private List<String> labels = new ArrayList<>();
    private List<String> errors = new ArrayList<>();

    private static void fireGet(String apiEndpoint, Consumer<String> onOk, Consumer<Integer> onNonOk, Runnable onIOError) {
        try {
            HttpRequestComposer.HttpResponse response = new HttpRequestComposer()
                    .auth(HttpRequestComposer.AuthType.BASIC, new Base64().encodeToString(creds.getBytes()))
                    // надо бы наловчиться получать это куки прямо как взаправду.... но мне лень =)
                    .setHeader("Cookie", "JSESSIONID=8FCCF738DF94666423D6FC3AD3DB6C4A")
                    .fire(String.format("https://jira.hillel.it/rest/api/2/%s", apiEndpoint));
            if (response.rescode == 200) onOk.accept(response.resBody);
            else onNonOk.accept(response.rescode);
        } catch (IOException e) {
            onIOError.run();
        }

    }

    public JiraIssuePoster forProject(String projectKey) {
        fireGet("project",
                (responseRaw) -> {
                    JSONArray projectsParsed = new JSONArray(responseRaw);
                    for (int i = 0; i < projectsParsed.length(); i += 1) {
                        if (projectsParsed.getJSONObject(i).getString("key").contentEquals(projectKey)) {
                            projectId = projectsParsed.getJSONObject(i).getInt("id");
                            this.projectKey = projectKey;
                            break;
                        }
                    }
                    if (projectId == -1) {
                        errors.add(String.format("Project with key %s does not exist.", projectKey));
                        this.projectId = 0;
                    }
                },
                (rescode) -> {
                    errors.add(String.format("Cannot determine project id for %s due to HTTP related problems: res code %d", projectKey, rescode));
                    projectId = 0;
                },
                () -> {
                    errors.add(String.format("Cannot determine project id for %s due to IO error", projectKey));
                    projectId = 0;
                });
        return this;
    }

    public JiraIssuePoster withPriority(String priorityFriendly) {
        fireGet("priority",
                (responseRaw) -> {
                    JSONArray prioritiesParsed = new JSONArray(responseRaw);
                    for (int i = 0; i < prioritiesParsed.length(); i += 1) {
                        if (prioritiesParsed.getJSONObject(i).getString("name").contentEquals(priorityFriendly)) {
                            priorityId = prioritiesParsed.getJSONObject(i).getInt("id");
                            break;
                        }
                    }
                },
                (rescode) -> {/* ignoring non-200 res codes as we still have default value for priority*/ },
                () -> {/* ignoring io errors as we still have default value for priority*/ });
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
        if (issueTypeFriendly != null && issueTypeFriendly.trim().length() > 0)
            issueType = issueTypeFriendly;
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
        if (summary == null)
            errors.add("Summary is either not set or only consists of whitespaces. Use summary().");
        if (projectId == -1) errors.add("Project is not set. Use withProject().");
        if (issueType == null) {
            final int[] issueTypeId = {-1};
            fireGet(String.format("issue/createMeta?projectKeys=%s&expand=projects.issuetypes.fields", projectKey),
                    (responseRaw) -> {
                        JSONArray issueTypesParsed = new JSONObject(responseRaw)
                                .getJSONArray("projects")
                                .getJSONObject(0)
                                .getJSONArray("issuetypes");
                        for (int i = 0; i < issueTypesParsed.length(); i += 1) {
                            if (issueTypesParsed.getJSONObject(i).getString("name").contentEquals(issueType)) {
                                issueTypeId[0] = issueTypesParsed.getJSONObject(i).getInt("id");
                                break;
                            }
                        }
                        if (issueTypeId[0] == -1) {
                            errors.add(String.format("Issue type %s does not exist in project %s", issueType, projectKey));
                            issueTypeId[0] = 0;
                        }
                    },
                    (rescode) -> {
                        errors.add(String.format("Cannot get issue types for project %s: HTTP res code %d", projectKey, rescode));
                        issueTypeId[0] = 0;
                    },
                    () -> {
                        errors.add(String.format("Cannot get issue types for project %s due to IO errors", projectKey));
                        issueTypeId[0] = 0;
                    }
            );
            if (issueTypeId[0] == -1)
                errors.add("Issue type is not set. Use ofType().");
            else { // as issue type is ultimate dependency, firing a new issue request only if everything OK with issue type
                JSONArray labels = new JSONArray();
                this.labels.stream().distinct().forEach(labels::put);

                JSONObject payload = new JSONObject();
                JSONObject fields = new JSONObject();
                fields.put("project", new JSONObject().put("id", projectId));
                fields.put("summary", summary);
                fields.put("description", description);
                fields.put("reporter", new JSONObject().put("name", ConfigurationManager.getInstance().getConfig("jiraUser").asString()));
                fields.put("issuetype", new JSONObject().put("id", issueTypeId[0]));
                fields.put("priority", new JSONObject().put("id", priorityId));
                fields.put("labels", labels);
                payload.put("fields", fields);

                final HttpRequestComposer.HttpResponse response = new HttpRequestComposer()
                        .auth(HttpRequestComposer.AuthType.BASIC, new Base64().encodeToString(creds.getBytes()))
                        .via(HttpRequestComposer.HTTPMethod.POST)
                        .withContentType(HttpRequestComposer.ContentType.JSON)
                        .payload(payload.toString())
                        // надо бы наловчиться получать это куки прямо как взаправду.... но мне лень =)
                        .setHeader("Cookie", "JSESSIONID=8FCCF738DF94666423D6FC3AD3DB6C4A")
                        .fire("https://jira.hillel.it/rest/api/2/issue");

                if (response.rescode == 201) {
                    JSONObject responseParsed = new JSONObject(response.resBody);
                    result = new JiraIssue(responseParsed.getString("key"), responseParsed.getInt("id"));
                }

            }
        }
        if (errors.size() > 0) {
            throw new IllegalStateException("Cannot create an issue:\n" + String.join("-n", errors));
        }

        return result;
    }
}
