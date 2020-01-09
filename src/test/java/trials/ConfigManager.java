package trials;

public class ConfigManager {
    private static ConfigManager instance;

    private String jiraUser;
    private String jiraPwd;

    private ConfigManager() {

        jiraUser = System.getProperty("jiraUser");
        jiraPwd = System.getProperty("jiraPwd");

    }

    public static ConfigManager getInstance() {
        if (instance == null)
            instance = new ConfigManager();
        return instance;
    }

    public String getJiraUser() {
        return jiraUser;
    }

    public String getJiraPwd() {
        return jiraPwd;
    }
}
