package trials;

import infra.Browser;
import infra.Browserfactory;
import infra.LoginPage;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SecondJiraTest {
    @Test
    public void AdvancedTest() throws InterruptedException {
        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
        driver.get("https://jira.hillel.it/browse/AQA919-7");
        LoginPage.login(driver); //(login with maven only)
        WebDriverWait wait = new WebDriverWait(driver, 20); //объявляем waiter

/* Labels */
        Actions labelsBuilder = new Actions(driver);
        WebElement LabelField = driver.findElement(By.cssSelector("#labels-68066-value"));
        labelsBuilder.moveToElement(LabelField).build().perform();//эмулируем наведение курсором

        wait.until(ExpectedConditions
             .visibilityOfElementLocated(By.cssSelector("#wrap-labels > div > span")))
             .click();
        WebElement labelTextArea = driver
              .findElement(By.xpath("/html//textarea[@id='labels-textarea']"));
        wait.until(ExpectedConditions.visibilityOf(labelTextArea));
        labelTextArea.sendKeys("Test_bla2");
        labelTextArea.submit();
        //  driver.findElement(By.cssSelector(".aui-icon.aui-icon-small.aui-iconfont-success"))
        //     .click();

        Thread.sleep(5000);
        System.out.println("Список лэйблов:");
        List<WebElement> lables = driver.findElements(By.className("lozenge"));
        for (WebElement labl : lables) {
            System.out.println(labl.getText());
            }

/* Priorities */
        driver.findElement(By.xpath("//*[@id=\"priority-val\"]")).click();
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//*[@id=\"priority-val\"]")))
                .getText();
        Thread.sleep(3000);
        List<WebElement> prior = driver
                .findElements(By.xpath("//*[@id=\"priority-suggestions\"]/div/ul/li/a"));
        System.out.println("Список приоритетовв:");
        for (WebElement sug : prior) {
            System.out.println(sug.getText());
        }
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[@id='priority-single-select']/span")))
                .click();
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//*[@id=\"priority-form\"]/div[2]/button[1]")))
                .click();
        Thread.sleep(1000);

/*  Description */
//        Actions builder = new Actions(driver);
//        WebElement descr = driver.findElement(By.cssSelector("div#description-val > .aui-icon.aui-icon-small.aui-iconfont-edit"));
//        builder.moveToElement(descr).build().perform();
//        driver.findElement(By.xpath("//div[@id='description-val']//em[.='Click to add description']")).click();


        driver.quit();
}

}
