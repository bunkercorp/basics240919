package trials;

import infra.Browser;
import infra.Browserfactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class ThirdTest {

    @Test

    private void writeToDiscription () throws InterruptedException {

        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);

        driver.get("https://jira.hillel.it/browse/AQA919-8");
        LoginPage log = new LoginPage();
        log.login(driver);

        driver.findElement(By.xpath("//*[@id=\"description-val\"]")).click();

        driver.switchTo().frame("//*[@id=\"mce_0_ifr\"]");


        WebElement a = driver.findElement(By.xpath("//*[@id=\"description-form\"]/div[1]/div/div[1]/div"));
        Thread.sleep(2);
        a.sendKeys("ghjk");

       driver.close();



    }
}
