package trials;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ReleasesPage {
    public String openReleasePageNewTab(WebDriver driver) throws AWTException, InterruptedException {
        WebDriverWait waiting = new WebDriverWait(driver, 5);

        String openAtNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Actions actions = new Actions(driver);
        driver
            .findElement(By.xpath(
               "//a[@href='/projects/AQA919?selectedItem=com.atlassian.jira.jira-projects-plugin:release-page']"))
            .sendKeys(openAtNewTab);
        Thread.sleep(1000);
        Set<String> tabId = driver.getWindowHandles();
        Iterator<String> iter = tabId.iterator();
        iter.next();
        driver.switchTo().window(iter.next());
        Thread.sleep(1000);
//        actions.keyDown(Keys.CONTROL).sendKeys("2").perform();

        System.out.println(driver.getTitle());

        //collapse left sidebar
//        String rightSidebarOpen
////            = "//div[@class='aui-sidebar projects-sidebar fade-in aui-sidebar-fly-out' and @aria-expanded='true']";
//            = "//div[@aria-expanded='true']";
//        String collapseSidebarButton
//            = "//div[@data-tooltip='Expand sidebar ( [ )']";
//        if(driver.findElements(By.xpath(rightSidebarOpen)).isEmpty()) {
//            System.out.println("---------if statement is true!-----------");
////            driver.findElement(By.xpath(collapseSidebarButton)).click();
//            actions.sendKeys("]");
//        }
//        else System.out.println("-------if is false--------");

        WebElement releaseGetText = waiting.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//div[@class='aui-page-header-inner']/h1")));
        return releaseGetText.getText();
    }
    public String checkPresenceManageButton(WebDriver driver){

        String manageVersionButtonXpath = "//a[@id='manage-versions' and @class='aui-button']";
        return driver.findElement(By.xpath(manageVersionButtonXpath)).getText();
    }
}
