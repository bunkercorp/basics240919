package trials;

public class ConfigManager {

    private static ConfigManager instance = null;
    private String username;
    private String password;


    private ConfigManager(String a, String b) {
        username = a;
        password = b;
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager(System.getProperty("jiraUser"), System.getProperty("jiraPwd"));
        }
        return instance;
    }


    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
}
