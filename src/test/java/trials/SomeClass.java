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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.InputSource;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class SomeClass {
     String targetProduct = "Блендер Hilton SMS 8143";
    @Test
    public void firstTest() throws InterruptedException {
        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
        final JavascriptExecutor jexec = (JavascriptExecutor) driver;
        final String initialTab = driver.getWindowHandle();

        //login
        driver.manage().window().maximize();
        driver.get("https://rozetka.com.ua/");
        Thread.sleep(2000);

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='search-form__input-wrapper']/input")));



        searchField.sendKeys(targetProduct);
        driver.findElement(By.xpath("//button[@class='button button_color_green button_size_medium search-form__submit']")).click();

        //check search position is first



        Thread.sleep(3000);



//
//        WebElement pencil = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'labels-wrap')]//span[contains(@class, 'aui-iconfont-edit')]")));
//        pencil.click();


//
//        String searchFormSelector = "//div[@class='search-form__input-wrapper']";
//        WebElement searchForm = driver.findElement(By.xpath(searchFormSelector));
//        searchForm.sendKeys("Блендер Hilton SMS 8143");











        //assign ticket to current user
//        String assignXpath = "//a[@id='assign-to-me']";
//        WebElement assignBtn = driver.findElement(By.xpath(assignXpath));

//        assignBtn.click();
//        Thread.sleep(1000);
//
//        //check assignee pop-up msg
//        String popupAssigneeXpath = "//div[@class = 'aui-message closeable aui-message-success aui-will-close']";
//        driver.findElement(By.xpath(popupAssigneeXpath)).getText();
//        Assert.assertEquals("AQA919-5 has been assigned.","AQA919-5 has been assigned.");
//
//        String assigneeUser = driver.findElement(By.xpath
//                ("//*[@id='issue_summary_assignee_Andrey']")).getText();
//        Assert.assertEquals(assigneeUser,"AndreyMaydanyuk");
//

        driver.quit();

    }
}