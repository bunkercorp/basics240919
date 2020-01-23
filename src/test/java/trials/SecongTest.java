package trials;

import infra.Browser;
import infra.Browserfactory;
import infra.LoginPage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class SecongTest {
    @Test
    public void SecondTest() throws InterruptedException {
        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
        Actions actions = new Actions(driver);

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

        String currentWindowHandle = driver.getWindowHandle();
        WebElement formWrapper = driver.findElement(By.xpath("//*[@id='description-val']/div"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
       executor.executeScript("arguments[0].click();", formWrapper);
        Thread.sleep(3000);

        driver.switchTo().frame(new WebDriverWait(driver, 1).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='description-wiki-edit']//iframe"))));
        driver.findElement(By.xpath("//body/p")).sendKeys("Lorem ipsum");
        driver.switchTo().window(currentWindowHandle);
        driver.findElement(By.xpath("//button[@class='aui-button aui-button-primary submit']")).click();

//check text in description
        String testText = driver.findElement(By.xpath("//*[@id=\"description-val\"]/div")).getText();//get text

        if (!testText.matches("Lorem ipsum")) {
            System.out.println("try to enter the text one more time");
        } else {
            System.out.println("Text in description is: " + testText);
        }


// add labels
        WebDriverWait label = new WebDriverWait(driver, 10);
        WebElement labelButton = label.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"wrap-labels\"]/div")));
        actions.moveToElement(labelButton).perform();
        WebElement pencil = label.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'labels-wrap')]//span[contains(@class, 'aui-iconfont-edit')]")));
        pencil.click();


        WebElement labelText = driver.findElement(By.xpath("//*[@id=\"labels-textarea\"]"));
        labelText.sendKeys("new3");
        driver.findElement(By.xpath("//*[@id=\"labels-form\"]/div[2]/button[1]/span")).click();

//check labels
        List<WebElement> labls = driver.findElements(By.xpath("//ul[@class='labels']/li"));
        for (WebElement labl : labls) {
            System.out.println(labl.getAttribute("title"));
        }



        actions.moveToElement(driver.findElement(By.linkText("Releases"))).keyDown(Keys.COMMAND).sendKeys("t").click().build().perform();
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));



//Wait for the new tab to finish loading content
        String wait2 = driver.findElement(By.xpath("//*[@class='aui-page-header-inner']/h1")).getText();
        Assert.assertEquals(wait2, "Releases");

        String manageButton = driver.findElement(By.xpath("//*[@id='manage-versions']")).getText();
        Assert.assertEquals(manageButton,"Manage Versions");
        Thread.sleep(5000);
        driver.close();
        driver.switchTo().window(tabs.get(0));
        Thread.sleep(5000);
        driver.close();
    }
}