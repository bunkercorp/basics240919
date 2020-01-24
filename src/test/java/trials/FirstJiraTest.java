package trials;

import infra.Browser;
import infra.Browserfactory;
import infra.pages.LoginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class FirstJiraTest {
    @Test
    public void firstTest() throws InterruptedException, MalformedURLException {
        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
        driver.get("https://jira.hillel.it/browse/AQA919-2");    //open ticket page
        LoginPage.login(driver);  //login

        driver.findElement(By.xpath("//a[@id='assign-to-me']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='aui-flag-container']/div/div"))).getText();
        Assert.assertEquals("AQA919-2 has been assigned.", "AQA919-2 has been assigned.");

        String assignee = driver.findElement(By.xpath("//*[@id=\"issue_summary_assignee_kshedris\"]")).getText();
        Assert.assertEquals( assignee ,"Konstantin Shedris" );

        driver.quit();

    }



}