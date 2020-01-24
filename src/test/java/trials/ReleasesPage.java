package trials;

import infra.Browser;
import infra.Browserfactory;
import infra.LoginPage;
import org.junit.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReleasesPage {

        @Test
        public void releasesPage() throws InterruptedException {
            final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
            driver.get("https://jira.hillel.it/browse/AQA919-8");
            LoginPage.login(driver);

            String openPage = Keys.chord(Keys.CONTROL, Keys.RETURN);
            driver.findElement(By.linkText("Releases")).sendKeys(openPage);

            for (String Window : driver.getWindowHandles()) {
                driver.switchTo().window(Window);
            }

            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Releases"))).getText();
            Assert.assertEquals("Releases", "Releases");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Manage Versions"))).getText();
            Assert.assertEquals("Manage Versions", "Manage Versions");

            String currentUrl = driver.getCurrentUrl();
            Assert.assertEquals(currentUrl, "https://jira.hillel.it/projects/AQA919?selectedItem=com.atlassian.jira.jira-projects-plugin:release-page");

            driver.quit();

        }
    }

