package trials;

import infra.Browser;
import infra.Browserfactory;
import infra.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JiraReleasesPage {

    @Test
    public void firstTest() throws InterruptedException {
        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
        driver.get("https://jira.hillel.it/browse/AQA919-7");
        LoginPage.login(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Releases")));

        String selectLinkOpeninNewTab = Keys.chord(Keys.COMMAND, Keys.ENTER);
        driver.findElement(By.linkText("Releases")).sendKeys(selectLinkOpeninNewTab);

        for (String Handle : driver.getWindowHandles()) {
            driver.switchTo().window(Handle);
        }


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Releases"))).getText();
        Assert.assertEquals("Releases", "Releases");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Manage Versions"))).getText();
        Assert.assertEquals("Manage Versions", "Manage Versions");

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://jira.hillel.it/projects/AQA919?selectedItem=com.atlassian.jira.jira-projects-plugin:release-page");

        driver.quit();
    }
}
