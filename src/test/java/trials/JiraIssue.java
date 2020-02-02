package trials;

import java.util.Collection;

public class JiraIssue {


    private String projectKey;
    private String issueTypeDisplayName;
    private String priorityDisplayName;
    private Collection<String> labels;
    private String content;
    private String summary;

    public static class Builder {
        private JiraIssue newJiraIssue;

        public Builder() {
            newJiraIssue = new JiraIssue();
        }

        public Builder inProject(String projectKey) {
            newJiraIssue.projectKey = projectKey;
            return this;
        }

        public Builder ofType(String issueTypeDisplayName) {
            newJiraIssue.issueTypeDisplayName = issueTypeDisplayName;
            return this;
        }

        public Builder withPriority(String priorityDisplayName) {
            newJiraIssue.priorityDisplayName = priorityDisplayName;
            return this;
        }

        public Builder withLabels(Collection<String> labels) {
            newJiraIssue.labels = labels;
            return this;
        }

        public Builder withDescription(String content) {
            newJiraIssue.content = content;
            return this;
        }

        public Builder withSummary(String summary) {
            newJiraIssue.summary = summary;
            return this;
        }

        public JiraIssue create() {
            return newJiraIssue;
        }
    }
}
