package infra;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class Browserfactory {
    private static WebDriver driver = null;

    public static WebDriver getDriver(Browser browser) {
        if (driver == null) {
            final OS currentOS = OS.current();
            final String binPath = String.format("%s/bin/%s%s", System.getProperty("user.dir"), browser.driverPathInBin, currentOS.driverFileNameEnding);
            switch (browser) {
                case CHROME:
                    System.setProperty("webdriver.chrome.driver", binPath);
                    driver = new ChromeDriver();
                    break;
                case FIREFOX:
                    System.setProperty("webdriver.gecko.driver", binPath);
                    driver = new FirefoxDriver();
                    break;
            }
            driver.manage().window().maximize();
        }
        return driver;
    }
}
