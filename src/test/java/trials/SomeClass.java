package trials;

import infra.Browser;
import infra.Browserfactory;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;


public class SomeClass {


    @Test
    public void firstTest() throws InterruptedException {
        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);



//        final JavascriptExecutor jexec = (JavascriptExecutor) driver;
//        final String initTaltab = driver.getWindowHandle();

//        jexec.executeScript("window.open('about:blank', '_blank', 'top:500')");
//        final String newWindow = driver
//                .getWindowHandles()
//                .stream()
//                .filter(wh -> !wh.contentEquals(initTaltab))
//                .findFirst()
//                .get();
//        Thread.sleep(3000);


//        driver.getWindowHandles().forEach(wh -> System.out.println(wh));
//        driver.switchTo().window(newWindow);
//        driver.get("http://wikipedia.org");
//
//        Thread.sleep(3000);
//        driver.switchTo().window(initTaltab);

        driver.get("https://jira.hillel.it/browse/AQA919-6");
//        driver.findElement(By.xpath("//*[@id=\"login-form-username\"]")).sendKeys("Alexey");
//        driver.findElement(By.xpath("//*[@id=\"login-form-password\"]")).sendKeys("1aka7201");
//        driver.findElement(By.xpath("//*[@id=\"login-form-submit\"]")).click();

        ConfigManager configManager = ConfigManager.getInstance();

        LoginPage loginPage = new LoginPage();
        loginPage.login(configManager.getJiraUser(), configManager.getJiraPwd(), driver);
         driver.findElement(By.xpath("//*[@id=\"assign-to-me\"]")).click();
               Thread.sleep(1500);
         String result = driver.findElement(By.xpath("//*[@id=\"assignee-val\"]")).getText();
//        ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"assignee-val\"]"));
        Assert.assertEquals(result, "Alexey Timotin");
//        ExpectedConditions.elementToBeSelected()

//        driver.quit();


    }
}