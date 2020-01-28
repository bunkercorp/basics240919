package infra;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
                DesiredCapabilities capabilities = new DesiredCapabilities();
              //  capabilities.setPlatform(Platform.VISTA);
                capabilities.setBrowserName(browser.name().toLowerCase());
            //    capabilities.setVersion("43");
                URL url = null;
                try {
                    url = new URL("http://192.168.3.69:4444/wd/hub");
                } catch (Throwable ignored) {
                    System.out.println("In BrowserFactory::getDriver");
                    ignored.printStackTrace();
                }
                driver = new RemoteWebDriver(url, capabilities);
            } else
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
