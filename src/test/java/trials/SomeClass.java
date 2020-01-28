package trials;

import Pages.LoginPage;
import infra.Browser;
import infra.Browserfactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;

public class SomeClass {
    @Test
    public void firstTest() throws InterruptedException, AWTException {
//        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
        final WebDriver driver = Browserfactory.getDriver(Browser.FIREFOX);
        final JavascriptExecutor jexec = (JavascriptExecutor) driver;
        final String initialTab = driver.getWindowHandle();

        driver.manage().window().maximize();
        driver.get("https://jira.hillel.it/browse/AQA919-5");
        LoginPage.login(driver);
        Thread.sleep(1000);

//        Priority priority = new Priority();
//        priority.printPriorityList(driver);
//
//        Labels label = new Labels();
//        label.printLabels(driver);
//        Thread.sleep(1000);
//
//        label.addLabel(driver,"autoLabel");
        Thread.sleep(1000);

        Description description  = new Description();
        description.addDescription(driver,"some auto description");
        Thread.sleep(1000);
        Assert.assertEquals(description.getDescription(driver),"some auto description");

//        ReleasesPage release = new ReleasesPage();
//        release.openReleasePageNewTab(driver);
//
//        Assert.assertEquals(release.openReleasePageNewTab(driver),"Releases");
//
//        Assert.assertEquals(release.checkPresenceManageButton(driver),"Manage Versions");
//        String releasePageUrl
//        = "https://jira.hillel.it/projects/AQA919?selectedItem=com.atlassian.jira.jira-projects-plugin:release-page";
//        Assert.assertEquals(driver.getCurrentUrl(), releasePageUrl);
        driver.quit();

    }
}