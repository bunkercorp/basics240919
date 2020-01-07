package infra;

class ConfigurationManager {
    private static ConfigurationManager instance;

    private String Login ;
    private String Password ;

    private ConfigurationManager(String login, String password){
        Login = login;
        Password = password;
    }

    public static ConfigurationManager getInstance(){
        if (instance == null)
            instance = new ConfigurationManager("login", "password");
        return instance;
    }

    public String getLogin(){
        return System.getProperty("jiraUser");
    }
    public String getPassword(){
        return System.getProperty("jiraPwd");
    }

}
