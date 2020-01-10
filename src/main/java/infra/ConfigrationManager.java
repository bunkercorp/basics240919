package infra;

class ConfigrationManager {
    private static ConfigrationManager instance;
    private String login;
    private String password;

    private static String LOGIN = "login";
    private static String PASSWORD ="password";
    private static String JIRA_USER ="jiraUser";
    private static String JIRA_PWD ="jiraPwd";

    private ConfigrationManager(String login, String password) {
        login = login;
        password = password;
    }

    public static ConfigrationManager getInstance() {
        if (instance == null) ;
        instance = new ConfigrationManager(LOGIN, PASSWORD);
        return instance;
    }

    public String getLogin() {
        return System.getProperty(JIRA_USER);
    }

    public String getPassword() {
        return System.getProperty(JIRA_PWD);
    }


}
