package trials;

import infra.Browser;
import infra.Browserfactory;
import infra.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class DescriptionTab {

    @Test

    private void writeToDescription () throws InterruptedException {

        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);

        driver.get("https://jira.hillel.it/browse/AQA919-8");
        LoginPage log = new LoginPage();
        log.login(driver);

        String currentWindow = driver.getWindowHandle();
        WebElement formWrapper = driver.findElement(By.id("description-val"));
        formWrapper.click();



        driver.switchTo().frame(new WebDriverWait(driver, 1)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='description-wiki-edit']//iframe"))));
        driver.findElement(By.xpath("//body/p")).sendKeys("write bla bla");
       // Thread.sleep(2);
        driver.switchTo().window(currentWindow);
        formWrapper.findElement(By.xpath("//button[@type='submit' and @accesskey='s']")).click();



       driver.quit();



    }
}
