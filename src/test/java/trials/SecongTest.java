package trials;

import infra.Browser;
import infra.Browserfactory;
import infra.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;


public class SecongTest {
    @Test
    public void SecondTest() throws InterruptedException {
        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
        driver.get("https://jira.hillel.it/browse/AQA919-9");    //open ticket page
        LoginPage.login(driver);  //login
        driver.findElement(By.xpath("//*[@id=\"priority-val\"]")).click();
//type of the issue
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"priority-val\"]"))).getText();

        Thread.sleep(3000);
        List<WebElement> Type = driver.findElements(By.xpath("//*[@id=\"priority-suggestions\"]/div/ul/li/a"));
        for (WebElement varOfTypes : Type) {
            System.out.println(varOfTypes.getText());
        }
// add text into description field
        WebElement ele = driver.findElement(By.xpath("//*[@id=\"description-val\"]/div"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", ele);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"description-wiki-edit\"]/nav/div/div/ul/li[2]/a")).click();
        driver.findElement(By.id("description")).clear();
       WebDriverWait some_element = new WebDriverWait(driver,100);
        some_element.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"description-wiki-edit\"]/nav/div/div/ul/li[2]/a"))).click();
        WebElement text = driver.findElement(By.xpath("//*[@id=\"description\"]"));
        text.click();
        text.sendKeys("test text");
        Thread.sleep(3000);
        WebDriverWait Save = new WebDriverWait(driver,100);
        Save.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"description-form\"]/div[2]/div[2]/button[1]"))).click();

//check text in description
        String testText = driver.findElement(By.xpath("//*[@id=\"description-val\"]/div")).getText();//get text

        if (!testText.matches("test text")) {
            throw new RuntimeException("Text is not matched");
        } else {
            System.out.println("Text in description is: " + testText);
        }


// add labels
        WebElement label = driver.findElement(By.xpath("//*[@id=\"labels-68354-value\"]"));
        label.click();
        Thread.sleep(3000);
        WebElement labelText = driver.findElement(By.xpath("//*[@id=\"labels-textarea\"]"));
        labelText.sendKeys("khoroshko");
driver.findElement(By.xpath("//*[@id=\"labels-form\"]/div[2]/button[1]")).click();

//check labels
        Thread.sleep(3000);
        List<WebElement> labls = driver.findElements(By.xpath("//*[@id=\"labels\"]"));
        for (WebElement labl : labls) {
            System.out.println(labl.getText());
        }


    }
}