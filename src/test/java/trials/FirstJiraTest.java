package trials;

import infra.Browser;
import infra.Browserfactory;
import infra.LoginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstJiraTest {
    @Test
    public void firstTest() {
        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
        driver.get("https://jira.hillel.it/browse/AQA919-9");    //open ticket page
        LoginPage.login(driver);  //login
        WebElement AssignButton = driver.findElement(By.xpath("//a[@id='assign-to-me']"));
        AssignButton.click();
        String assignee = driver.findElement(By.xpath("// *[@id=\"issue_summary_assignee_tatiana.khoroshko\"]")).getText();//get text
        Assert.assertEquals( assignee ,"Tatiana.Khoroshko" );
    }


}