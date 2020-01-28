package trials;

import infra.Browser;

import infra.BrowserFactory;
import org.testng.Assert;
import pages.IssuePage;
import pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.net.MalformedURLException;

public class ClassTask {
//    WebDriver driver = null;
//
//    @BeforeClass
//    public void beforeSuite() throws MalformedURLException {
//        driver = BrowserFactory.getDriver(Browser.CHROME);
//    }


    private void goTicket(int key) {
//        final String jiraTicket = String.format("AQA919-%d", key);
//        String visitedPageJiraKey =(String) IssuePage
//                .instance(driver)
//                .visit(jiraTicket)
//                .getResult();
//        if (visitedPageJiraKey == null) {
//            LoginPage.instance(driver).login();
//            visitedPageJiraKey = IssuePage.instance().isOnScreen().getResult().toString();
//        }
//
//        Assert.assertEquals(visitedPageJiraKey, jiraTicket);
//
//        String currentWindowHandle = driver.getWindowHandle();
//        WebElement formWrapper = driver.findElement(By.id("description-val"));
//        formWrapper.click();
//        driver.switchTo().frame(new WebDriverWait(driver, 1).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='description-wiki-edit']//iframe"))));
//        driver.findElement(By.xpath("//body/p")).sendKeys("Lorem ipsum");
//        driver.switchTo().window(currentWindowHandle);
//        formWrapper.findElement(By.xpath("//button[@type='submit' and @accesskey='s']")).click();

    }

    @Test
    public void firstTest1() throws InterruptedException {
        goTicket(1);
    }

//    @Test
//    public void firstTest2() throws InterruptedException {
//        goTicket(2);
//    }
//
//    @Test
//    public void firstTest3() throws InterruptedException {
//        goTicket(7);
//    }
//
//    @Test
//    public void firstTest4() throws InterruptedException {
//        goTicket(31);
//    }
//
//    @Test
//    public void firstTest5() throws InterruptedException {
//        goTicket(25);
//    }


//    @AfterTest
//    public void teardown() throws InterruptedException {
//        Thread.sleep(3000);
//    }
//
//    @AfterSuite
//    public void suiteTeardown() {
//        driver.quit();
//    }


}
