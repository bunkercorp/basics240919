package infra;

import java.util.ArrayList;
import java.util.function.Function;

public enum OS {
    WINDOWS(".exe", s -> s.contains("win")),
    LINUX("_nix", s -> new ArrayList<String>() {{
        add("nix");
        add("nux");
        add("aix");
    }}.stream().anyMatch(s::contains)),
    MACOS("_mac", s -> s.contains("mac"));

    private static class UnrecognizedOSException extends RuntimeException {
        UnrecognizedOSException(String foundOSName){
            super(String.format("OS '%s' is not in list of known operating systems",foundOSName));
        }
    }

    private final Function<String, Boolean> predicate;
    public final String driverFileNameEnding;

     OS(String fne, Function<String, Boolean> p) {
        this.driverFileNameEnding = fne;
        this.predicate = p;
    }

    public static OS current() {
        final String currentOS = System.getProperty("os.name").toLowerCase();
        OS result = null;
        for (OS candidate : OS.values()) {
            if (candidate.predicate.apply(currentOS)) {
                result = candidate;
                break;
            }
        }
        if(result == null)
            throw new UnrecognizedOSException(currentOS);

        return result;
    }
}
