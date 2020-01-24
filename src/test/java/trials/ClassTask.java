package trials;

import infra.Browser;
import infra.Browserfactory;
import infra.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.net.MalformedURLException;

public class ClassTask {
    WebDriver driver = null;

    @BeforeSuite
    public void beforeSuite() throws MalformedURLException {
        driver = Browserfactory.getDriver(Browser.CHROME);
    }
private void goTicket(int key){
    driver.get(String.format("https://jira.hillel.it/browse/AQA919-%d", key));
    LoginPage.login(driver);
    String currentWindowHandle = driver.getWindowHandle();
    WebElement formWrapper = driver.findElement(By.id("description-val"));
    formWrapper.click();
    driver.switchTo().frame(new WebDriverWait(driver, 1).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='description-wiki-edit']//iframe"))));
    driver.findElement(By.xpath("//body/p")).sendKeys("Lorem ipsum");
    driver.switchTo().window(currentWindowHandle);
    formWrapper.findElement(By.xpath("//button[@type='submit' and @accesskey='s']")).click();

}

    @Test
    public void firstTest1() throws InterruptedException {
     goTicket(1);
    }
    @Test
    public void firstTest2() throws InterruptedException {
        goTicket(2);
    }
    @Test
    public void firstTest3() throws InterruptedException {
        goTicket(7);
    }
    @Test
    public void firstTest4() throws InterruptedException {
        goTicket(31);
    }
    @Test
    public void firstTest5() throws InterruptedException {
        goTicket(25);
    }



    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(3000);
    }

    @AfterSuite
    public void suiteTeardown() {
        driver.quit();
    }


}
