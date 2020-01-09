package trials;

import infra.Browser;
import infra.Browserfactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class SomeClass {
    @Test
    public void firstTest() throws InterruptedException {



        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
        final JavascriptExecutor jexec = (JavascriptExecutor) driver;
        final String initialTab  = driver.getWindowHandle();
       jexec.executeScript("window.open('about:blank', '_blank', 'top:500')");
        final String newWindow = driver.getWindowHandles().stream()
                .filter(wh->wh.contentEquals(initialTab))
                .findFirst()
                .get();
        Thread.sleep(3000);
        //driver.getWindowHandles().forEach(wh -> System.out.println());
        driver.switchTo().window(newWindow);
        driver.get("http://wikipedia.org");
        Thread.sleep(3000);
        driver.switchTo().window(initialTab);
        driver.close();
    }
}