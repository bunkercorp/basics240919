package trials;

import com.google.gson.internal.bind.util.ISO8601Utils;
import infra.Browser;
import infra.Browserfactory;
import infra.SeleniumUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class SomeClass {
    @Test
    public void firstTest() throws InterruptedException {
        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
        final JavascriptExecutor jexec = (JavascriptExecutor) driver;
        final String intialTab = driver.getWindowHandle();

        jexec.executeScript("window.open('about:blank')");

        final String newWindow = driver
                .getWindowHandles()
                .stream()
                .filter(wh -> !wh.contentEquals(intialTab))
                .findFirst()
                .get();

        Thread.sleep(3000);

//        driver.getWindowHandles().forEach(wh > System.out.println(wh));
        driver.switchTo().window(newWindow);
        driver.get("http://wikipedia.org");



        Thread.sleep(3000);
        driver.switchTo().window(intialTab);
        String wikitabHandler = SeleniumUtils.switchToWindow("Wikipedia", driver);
        if (wikitabHandler !=null) {
            driver.switchTo().window(wikitabHandler);
        }
        driver.quit();

    }
}