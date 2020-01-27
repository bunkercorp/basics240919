package infra;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Supplier;

public class ConfigurationManager {
    public static final class ConfigItem {
        private final Object item;

        protected ConfigItem(Object o) {
            item = o;
        }

        public Integer asInt() {
            return (Integer) item;
        }

        public String asString() {
            return (String) item;
        }

        public Boolean asFlag() {
            return (Boolean) item;
        }

    }

    private static ConfigurationManager instance;

    private final HashMap<String, ConfigItem> configurationItems = new HashMap<>();

    private static final HashMap<String, Supplier<Object>> parsingRules = new HashMap<String, Supplier<Object>>() {{
        put("jiraUser", () -> System.getProperty("jiraUser"));
        put("jiraPwd", () -> System.getProperty("jiraPwd"));
        put("projectBaseUrl", () -> "https://jira.hillel.it");
        put("isRemoteRun", () -> System.getenv("REMOTE_RUN") != null && System.getenv("REMOTE_RUN").toLowerCase().contentEquals("true"));
    }};


    private ConfigurationManager() {

    }

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
            StringBuilder errorSB = new StringBuilder();
            parsingRules.forEach((configItemName, configItemSupplier) -> {
                try {
                    instance.configurationItems.put(configItemName, new ConfigItem(configItemSupplier.get()));
                } catch (Throwable t) {
                    t.getStackTrace();
                    errorSB.append(String.format("\t\"%s\"", configItemName));
                    errorSB.append(String.format("\t\t%s", t.getMessage()));
                    Arrays.stream(t.getStackTrace()).forEach(sti -> errorSB.append(String.format("\t\t\t%s", sti.toString())));
                }
            });
            if (errorSB.length() > 0)
                throw new IllegalStateException(errorSB.toString());
        }
        return instance;
    }

    public ConfigItem getConfig(String itemName) {
        return configurationItems.get(itemName);
    }


}
