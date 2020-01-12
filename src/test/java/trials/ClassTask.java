package trials;

import infra.Browser;
import infra.Browserfactory;
import infra.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.List;

public class ClassTask {
    @Test
    public void firstTest() throws InterruptedException {
        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
        driver.get("https://jira.hillel.it/browse/AQA919-2");
        LoginPage.login(driver);

        driver.findElement(By.xpath("//*[@id=\"priority-val\"]")).click();


        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"priority-val\"]"))).getText();

        Thread.sleep(3000);
        List<WebElement> labls = driver.findElements(By.xpath("//*[@id=\"priority-suggestions\"]/div/ul/li/a"));
        for (WebElement labl : labls) {
            System.out.println(labl.getText());
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='priority-single-select']/span"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"priority-form\"]/div[2]/button[1]"))).click();

        Actions builder = new Actions(driver);
        WebElement element = driver.findElement(By.cssSelector("#wrap-labels > div[title='Click to edit']"));
        builder.moveToElement(element).build().perform();

        WebElement howerlement = driver.findElement(By.cssSelector("div#wrap-labels  .aui-icon.aui-icon-small.aui-iconfont-edit"));
        howerlement.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html//textarea[@id='labels-textarea']"))).sendKeys("test3");
        Thread.sleep(2000);

        driver.findElement(By.xpath("/html//textarea[@id='labels-textarea']")).sendKeys(Keys.RETURN);

        driver.findElement(By.cssSelector(".aui-icon.aui-icon-small.aui-iconfont-success")).click();
        Thread.sleep(5000);

        builder = new Actions(driver);
        element = driver.findElement(By.cssSelector("#wrap-labels > div[title='Click to edit']"));
        builder.moveToElement(element).build().perform();

        howerlement = driver.findElement(By.cssSelector("div#wrap-labels  .aui-icon.aui-icon-small.aui-iconfont-edit"));
        howerlement.click();

        Thread.sleep(5000);
        List<WebElement> lables = driver.findElements(By.className("value-item"));
        for (WebElement labl : lables) {
            System.out.println(labl.getText());
            Thread.sleep(40000);


            driver.findElement(By.cssSelector(".aui-icon.aui-icon-small.aui-iconfont-success")).click();





        }
    }
}
