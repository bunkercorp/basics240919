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
import java.util.Optional;
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

    private static String jSessionId = null;

    static {
        try {
            HttpRequestComposer.HttpResponse response = new HttpRequestComposer()
                    .auth(HttpRequestComposer.AuthType.BASIC, new Base64().encodeToString(creds.getBytes()))
                    .via(HttpRequestComposer.HTTPMethod.HEAD)
                    .fire("https://jira.hillel.it/");

            Optional<String> jSessionIdCandidate = response.getResponseHeaders().get("Set-Cookie")
                    .stream()
                    .filter(cookieToSet -> cookieToSet.startsWith("JSESSIONID="))
                    .findFirst();

            jSessionIdCandidate.ifPresent(s -> jSessionId = s
                    .split(";")[0]
                    .replace("JSESSIONID=", "")
                    .trim());
        } catch (IOException ignored) {
        }
    }

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
                    .setHeader("Cookie", String.format("JSESSIONID=%s", jSessionId))
                    .fire(String.format("https://jira.hillel.it/rest/api/2/%s", apiEndpoint));
            if (response.resCode == 200) onOk.accept(response.resBody);
            else onNonOk.accept(response.resCode);
        } catch (IOException e) {
            onIOError.run();
        }
    }

    private static boolean assureNonDummy(String candidate) {
        return candidate != null && !candidate.trim().isEmpty();
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
        if (assureNonDummy(summary))
            this.summary = summary;
        return this;
    }

    public JiraIssuePoster withDescription(String description) {
        if (assureNonDummy(description))
            this.description = description;
        return this;
    }

    public JiraIssuePoster ofType(String issueTypeFriendly) {
        if (assureNonDummy(issueTypeFriendly))
            issueType = issueTypeFriendly;
        return this;
    }

    public JiraIssuePoster label(String label) {
        if (assureNonDummy(label))
            labels.add(label);
        return this;
    }

    public JiraIssuePoster labels(Collection<String> labels) {
        labels.forEach(label -> {
            if (assureNonDummy(label))
                this.labels.add(label);
        });
        return this;
    }

    public JiraIssue create() throws IOException {
        JiraIssue result = null;
        if (summary == null)
            errors.add("Summary is either not set or only consists of whitespaces. Use summary().");
        if (projectId == -1) errors.add("Project is not set. Use withProject().");
        if (issueType == null)
            errors.add("Issue type is not set. Use ofType().");
        else if (projectId > 0) {
            final int[] issueTypeId = {-1};
            fireGet(String.format("project/%d", projectId),
                    (responseRaw) -> {
                        JSONArray issueTypesParsed = new JSONObject(responseRaw)
                                .getJSONArray("issueTypes");
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
            if (issueTypeId[0] > 0) { // as issue type is ultimate dependency, firing a new issue request only if everything OK with issue type
                JSONArray labels = new JSONArray();
                this.labels.stream().distinct().forEach(labels::put);

                JSONObject payload = new JSONObject();
                JSONObject fields = new JSONObject();
                fields.put("project", new JSONObject().put("id", projectId));
                fields.put("summary", summary);
                fields.put("description", description);
                fields.put("reporter", new JSONObject().put("name", ConfigurationManager.getInstance().getConfig("jiraUser").asString()));
                fields.put("issuetype", new JSONObject().put("id", issueTypeId[0]));
                fields.put("priority", new JSONObject().put("id", "" + priorityId));
                fields.put("labels", labels);
                payload.put("fields", fields);

                final HttpRequestComposer.HttpResponse response = new HttpRequestComposer()
                        .auth(HttpRequestComposer.AuthType.BASIC, new Base64().encodeToString(creds.getBytes()))
                        .via(HttpRequestComposer.HTTPMethod.POST)
                        .withContentType(HttpRequestComposer.ContentType.JSON)
                        .payload(payload.toString())
                        .setHeader("Cookie", String.format("JSESSIONID=%s", jSessionId))
                        .fire("https://jira.hillel.it/rest/api/2/issue");

                if (response.resCode == 201) {
                    JSONObject responseParsed = new JSONObject(response.resBody);
                    result = new JiraIssue(responseParsed.getString("key"), responseParsed.getInt("id"));
                } else errors.add(String.format("Cannot create an issue: HTTP response code %d", response.resCode));
            }
        }
        if (errors.size() > 0) {
            throw new IllegalStateException("Cannot create an issue:\n\t" + String.join("\n\t", errors));
        }

        return result;
    }
}
