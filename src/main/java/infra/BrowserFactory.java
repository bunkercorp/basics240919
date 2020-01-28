package infra;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BrowserFactory {
    private static WebDriver driver = null;

    public static WebDriver getDriver() {
        if (driver == null) {
            final Browser browser = ConfigurationManager.getInstance().getConfig("browser", Browser.class);
            final OS currentOS = OS.current();
            final String binPath = String.format("%s/bin/%s%s", System.getProperty("user.dir"),
                    browser.driverPathInBin,
                    currentOS.driverFileNameEnding);
            if (ConfigurationManager.getInstance().getConfig("isRemoteRun", Boolean.class)) {
                Capabilities cap = new DesiredCapabilities();
                URL url = null;
                try {
                    url = new URL("http://192.168.3.69:4444/wd/hub");
                } catch (Throwable ignored) {
                    System.out.println("In BrowserFactory::getDriver");
                    ignored.printStackTrace();
                }
                driver = new RemoteWebDriver(url, cap);
            } else
                switch (browser) {
                    case CHROME:
                        System.setProperty("webdriver.chrome.driver", binPath);
                        driver = new ChromeDriver();
                        break;
                    case FIREFOX:
                        //FIXME
                        break;
                }

            driver.manage().window().maximize();
        }
        return driver;
    }
}
