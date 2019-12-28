package trials;

import infra.Browser;
import infra.Browserfactory;
import infra.LoginPage;
import infra.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.security.auth.login.Configuration;


public class FirstJiraTest {
    @Test
    public void FirstTest(){
        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
        driver.get("https://jira.hillel.it/browse/AQA919-7");
        LoginPage.login(driver); //(login with maven only)

        driver.findElement(By.xpath("//*[@id=\"assign-to-me\"]")).click(); //assign to me
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("aui-message-success"))).getText();
        Assert.assertEquals("AQA919-7 has been assigned.", "AQA919-7 has been assigned.");

        String assignee = driver.findElement(By.xpath("//*[@id=\"issue_summary_assignee_ekaterinaF\"]")).getText();//get text
        Assert.assertEquals(assignee, "Ekaterina Filippova"); //сравнить значения

        driver.close();




        //class="aui-message closeable aui-message-success aui-will-close"
        //AQA919-7 has been assigned.
}
}
