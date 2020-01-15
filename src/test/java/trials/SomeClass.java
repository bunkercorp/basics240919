package trials;

import infra.Browser;
import infra.Browserfactory;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;


public class SomeClass {


    @Test
    public void firstTest() throws InterruptedException {
        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);

        driver.get("https://jira.hillel.it/browse/AQA919-6");

        ConfigManager configManager = ConfigManager.getInstance();

        LoginPage loginPage = new LoginPage();
        loginPage.login(configManager.getJiraUser(), configManager.getJiraPwd(), driver);
         driver.findElement(By.xpath("//*[@id=\"assign-to-me\"]")).click();
               Thread.sleep(2000);
         String result = driver.findElement(By.xpath("//*[@id=\"assignee-val\"]")).getText();
        ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"assignee-val\"]"));
        Assert.assertEquals(result, "Alexey Timotin");



//        driver.quit();


    }
}