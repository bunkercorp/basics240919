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

public class ClassTask {
    WebDriver driver = null;

    @BeforeSuite
    public void beforeSuite() {
        driver = Browserfactory.getDriver(Browser.CHROME);
    }

    @BeforeTest
    public void navigate() {
        driver.get("https://jira.hillel.it/browse/AQA919-2");
        LoginPage.login(driver);
    }

    @Test
    public void firstTest() throws InterruptedException {
        String currentWindowHandle = driver.getWindowHandle();
        WebElement formWrapper = driver.findElement(By.id("description-val"));
        formWrapper.click();
        driver.switchTo().frame(new WebDriverWait(driver, 1).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='description-wiki-edit']//iframe"))));
        driver.findElement(By.xpath("//body/p")).sendKeys("Lorem ipsum");
        driver.switchTo().window(currentWindowHandle);
        formWrapper.findElement(By.xpath("//button[@type='submit' and @accesskey='s']")).click();
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
