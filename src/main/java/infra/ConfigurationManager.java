package infra;

public class ConfigurationManager {
    private static ConfigurationManager instance;
    private String login;
    private String password;

    private static String LOGIN = "login";
    private static String PASSWORD = "password";
    private static String JIRA_USER = "jiraUser";
    private static String JIRA_PWD = "jiraPwd";

    private ConfigurationManager(String login, String password) {
        login = login;
        password = password;
    }

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                instance = new ConfigurationManager(LOGIN, PASSWORD);
            }
        }
        return instance;
    }

    public String getLogin() {
        return System.getProperty(JIRA_USER);
    }

    public String getPassword() {
        return System.getProperty(JIRA_PWD);
    }


}
