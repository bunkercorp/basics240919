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

public class ReleasesTab {
    @Test
    public void firstTest() throws InterruptedException {
        final WebDriver driver = Browserfactory.getDriver(Browser.FIREFOX);
        driver.get("https://jira.hillel.it/browse/AQA919-2");
        LoginPage.login(driver);

        String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
        driver.findElement(By.linkText("Releases")).sendKeys(selectLinkOpeninNewTab);

        for (String Handle : driver.getWindowHandles()) {
            driver.switchTo().window(Handle);
        }
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Releases"))).getText();
        Assert.assertEquals("Releases", "Releases");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Manage Versions"))).getText();
        Assert.assertEquals("Manage Versions", "Manage Versions");

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://jira.hillel.it/projects/AQA919?selectedItem=com.atlassian.jira.jira-projects-plugin:release-page");

        driver.quit();

    }
}

