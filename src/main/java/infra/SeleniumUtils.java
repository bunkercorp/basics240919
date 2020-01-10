package infra;

import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Set;

public class SeleniumUtils {
    public static String switchToWindow(String windowTitle, WebDriver driver) {
       // String windowHandler = null;
        Set<String> knownHandlers = driver.getWindowHandles();
        for (String candidateHandler : knownHandlers) {
            driver.switchTo().window(candidateHandler);
            String candidateTitle = driver.getTitle();
            if (candidateTitle.contentEquals(windowTitle)) {
                return  candidateHandler;

            }
        }
        return null;
    }

}
