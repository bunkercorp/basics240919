package trials;

import Pages.LoginPage;
import com.google.gson.internal.bind.util.ISO8601Utils;
import infra.Browser;
import infra.Browserfactory;
import infra.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

public class SomeClass {
    @Test
    public void firstTest() throws InterruptedException {
        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
        final JavascriptExecutor jexec = (JavascriptExecutor) driver;
        final String initialTab = driver.getWindowHandle();

        //login
        driver.manage().window().maximize();
        driver.get("https://jira.hillel.it/browse/AQA919-5");
        LoginPage.login(driver);
        Thread.sleep(300);

        //assign ticket to current user
        String assignXpath = "//a[@id='assign-to-me']";
        WebElement assignBtn = driver.findElement(By.xpath(assignXpath));

        assignBtn.click();
        Thread.sleep(1000);

        //check assignee pop-up msg
        // Не работает. Возможно следует добавить вейтер?
        String popupAssigneeXpath = "//div[@class = 'aui-message closeable aui-message-success aui-will-close']";
        driver.findElement(By.xpath(popupAssigneeXpath)).getText();
        Assert.assertEquals("AQA919-5 has been assigned.","AQA919-5 has been assigned.");

        String assigneeUser = driver.findElement(By.xpath
                ("//*[@id='issue_summary_assignee_Andrey']")).getText();
        Assert.assertEquals(assigneeUser,"AndreyMaydanyuk");


        driver.quit();

    }
}