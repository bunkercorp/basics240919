package trials;

import infra.Browser;
import infra.Browserfactory;
import infra.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class JiraPage {

    @Test

    private void jiraPage() throws InterruptedException {
        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);

        driver.get("https://jira.hillel.it/browse/AQA919-8");
        LoginPage log = new LoginPage();
        log.login(driver);

        /* Priority list*/

        driver.findElement(By.xpath("//*[@id=\"priority-val\"]")).click();

        Thread.sleep(500);

        List<WebElement> webElementList = driver.findElements(By.xpath("//*[@id=\"priority-suggestions\"]/div/ul/li/a"));
        for (WebElement text : webElementList) {
            System.out.println(text.getText());
        }

        Thread.sleep(5000);

        /* Label list*/

        WebDriverWait wait = new WebDriverWait(driver, 20);

        Actions builder = new Actions(driver);
        WebElement labels = driver.findElement(By.cssSelector("#wrap-labels > div[title='Click to edit']"));
        builder.moveToElement(labels).build().perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#wrap-labels  .aui-icon.aui-icon-small.aui-iconfont-edit"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html//textarea[@id='labels-textarea']"))).sendKeys("ASAP");

        driver.findElement(By.cssSelector(".aui-icon.aui-icon-small.aui-iconfont-success")).click();

        Thread.sleep(4000);

        List<WebElement> webElements = driver.findElements(By.xpath("//*[@id=\"labels-68067-value\"]/li/a"));

        for (WebElement text : webElements) {
            System.out.println(text.getText());

        }

        driver.close();


    }
}




