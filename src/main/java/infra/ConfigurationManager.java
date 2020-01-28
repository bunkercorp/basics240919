package infra;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;

public class ConfigurationManager {
    private static ConfigurationManager instance;

    private final HashMap<String, Object> configurationItems = new HashMap<>();

    private static final HashMap<String, Supplier<Object>> parsingRules = new HashMap<String, Supplier<Object>>() {{
        put("browser", () -> {
            final String browserProperty = System.getProperty("browser");
            if (browserProperty != null) {
                final Optional<Browser> browserCandidate = Arrays.stream(Browser.values())
                        .filter(browser -> browserProperty.toUpperCase().contentEquals(browser.name()))
                        .findFirst();

                if (browserCandidate.isPresent()) {
                    return browserCandidate.get();
                } else
                    throw new IllegalArgumentException(String.format("browser property is set to %s which is not a known browser", browserProperty));
            } else throw new IllegalArgumentException("browser property is not set.");
        });
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
                    instance.configurationItems.put(configItemName, configItemSupplier.get());
                } catch (Throwable t) {
                    t.printStackTrace();
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

    public <T> T getConfig(String itemName, Class<T> asClass) {
        return asClass.cast(configurationItems.get(itemName));
    }


}
