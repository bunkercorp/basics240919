package infra;

public class ConfigurationManager {
    private static ConfigurationManager instance;
    private String login, password;

    private static String LOGIN = "login";
    private static String PASSWORD = "password";
    private static String JIRAUSER = "jirauser";
    private static String JIRAPWD = "jirapwd";

    private ConfigurationManager(String login, String password){

        this.login = login;
        this.password = password;
    }

    private static ConfigurationManager getInstance(){
        if(instance==null) {
            synchronized (ConfigurationManager.class){
                instance = new ConfigurationManager(LOGIN, PASSWORD);
            }
        }
        return  instance;
    }

    public String getLogin(){
        return System.getProperty(JIRAUSER);
    }

    public String getPassword(){
        return System.getProperty(JIRAPWD);
    }
}
