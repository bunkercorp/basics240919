package trials;

import infra.Browser;
import infra.Browserfactory;
import infra.LoginPage;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import static junit.framework.TestCase.assertTrue;

public class SimpleTest {

    @Test
    private void assignJira(){

        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
        driver.get("https://jira.hillel.it/browse/AQA919-8");
        LoginPage log =  new LoginPage();
        log.login( driver);

        driver.findElement(By.xpath("//*[@id=\"assign-to-me\"]")).click();

        WebElement message = (new WebDriverWait(driver, 5))
                .until((ExpectedCondition<WebElement>) d -> d.findElement(By.xpath("//*[@id=\"aui-flag-container\"]/div/div")));
        assertTrue(message.getText().contains("AQA919-8 has been assigned."));

          String text = driver.findElement(By.xpath("//*[@id=\"assignee-val\"]")).getText();

         Assert.assertEquals(text, "Olha Arnaut");
    }
}