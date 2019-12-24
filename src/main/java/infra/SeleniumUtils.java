package infra;

import org.openqa.selenium.WebDriver;

import java.util.Set;

public class SeleniumUtils {
    public static String switchToWindow(String windowTitle, WebDriver driver){
        String windowHandler = null;
        Set<String> knowHandlers = driver.getWindowHandles();
        for (String candidate:knowHandlers){
            driver.switchTo().window(candidate);
            String candidateTitle = driver.getTitle();
            if (candidateTitle.contentEquals(windowTitle)){
                windowHandler = candidate;
                break;
            }
        }


        driver.getTitle();
        return windowHandler;
    }
}
