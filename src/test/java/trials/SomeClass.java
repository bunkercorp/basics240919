package trials;

import Pages.LoginPage;
import infra.Browser;
import infra.Browserfactory;
import infra.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SomeClass {
    @Test
    public void firstTest() throws InterruptedException {
        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
        final JavascriptExecutor jexec = (JavascriptExecutor) driver;
        final String initialTab = driver.getWindowHandle();

        // jexec.executeScript("window.open('about:blank', '_blank', 'top:500')");
        //jexec.executeScript("window.open('about:blank')");
//        final String newWindow = driver
//                .getWindowHandles()
//                .stream()
//                .filter(wh -> !wh.contentEquals(initialTab))
//                .findFirst()
//                .get();
//        Thread.sleep(3000);
        //driver.getWindowHandles().forEach(wh -> System.out.println(wh));
        //driver.switchTo().window(newWindow);
        driver.get("https://jira.hillel.it/browse/AQA919-4");
        driver.manage().window().maximize();
        LoginPage.login(driver);
        Thread.sleep(3000);
        String xpathAssignToMe = "//a[@id='assign-to-me']";
        driver.findElement(By.xpath(xpathAssignToMe)).click();
        String newName = "//span[@id='issue_summary_assignee_n.n.kopko']";
        String xpathName = driver.findElement(By.xpath(newName)).getText().trim();
        String xpathName2="Niko";
        Assert.assertEquals(xpathName,xpathName2);
        Thread.sleep(2000);
        String xpathPop = "//div[@class='aui-message closeable aui-message-success aui-will-close']";
        String xpathPopup = driver.findElement(By.xpath(xpathPop)).getText().trim();
        String xpathPopup2="AQA919-4 has been assigned.";
        Assert.assertEquals(xpathPopup,xpathPopup2);


        //Thread.sleep(3000);
        // driver.switchTo().window(initialTab);
        // driver.close();
        //    driver.switchTo().window(wikiTabHandler);
    }
}