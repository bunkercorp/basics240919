package Pages;

public class ConfigManager {

    private static ConfigManager instance = null;
    private String userName;
    private String userPwd;

    private ConfigManager(String x, String y) {
        userName = System.getProperty(x);
        userPwd = System.getProperty(y);
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager("jiraUser", "jiraPwd");
        }
        return instance;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPwd() {

        return userPwd;
    }
}
