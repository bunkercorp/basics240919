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
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class SomeClass {
    @Test
    public void firstTest() throws InterruptedException {
        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
        final JavascriptExecutor jexec = (JavascriptExecutor) driver;
        final String intialTab = driver.getWindowHandle();

        //login
        driver.manage().window().maximize();
        driver.get("https://jira.hillel.it/browse/AQA919-5");
        LoginPage.login(driver);
        Thread.sleep(500);

        //assign ticket to current user
        String assignXpath = "//a[@id='assign-to-me']";
        WebElement assignBtn = driver.findElement(By.xpath(assignXpath));

        assignBtn.click();
        Thread.sleep(1000);

        //check assignee pop-up msg
        String popupAssigneeXpath = "//div[@class = 'aui-message closeable aui-message-success aui-will-close']";
        WebElement popupAssignee = driver.findElement(By.xpath(popupAssigneeXpath));

        if (popupAssignee.getText().equals("AQA919-5 has been assigned."))
            System.out.println("Ticket assigned successfully!");

        driver.quit();

    }
}