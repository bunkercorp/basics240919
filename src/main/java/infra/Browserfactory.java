package infra;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

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
                    //FIXME
                    break;
            }
            //http://google.com.ua/search?q=How+to+rule+the+world+without+being+noticed+by+nurses

            driver.manage().window().maximize();
            driver.get("https://jira.hillel.it/browse/AQA919-4");
            ((JavascriptExecutor) driver).executeScript("(()=> alert('Hello there!'))()");
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.switchTo().alert().dismiss();
        }
        return driver;
    }
}
