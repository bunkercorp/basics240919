package trials;

import infra.Browser;
import infra.Browserfactory;
import infra.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class SecongTest {
    @Test
    public void firstTest() {
        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
        driver.get("https://jira.hillel.it/browse/AQA919-9");    //open ticket page
        LoginPage.login(driver);  //login


    }
