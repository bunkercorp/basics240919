package trials;

import Pages.LoginPage;
import infra.Browser;
import infra.Browserfactory;
import infra.SeleniumUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class SomeClass {
    @Test
    public void firstTest() throws InterruptedException {
        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
        final JavascriptExecutor jexec = (JavascriptExecutor) driver;
        final String initialTab = driver.getWindowHandle();

        // jexec.executeScript("window.open('about:blank', '_blank', 'top:500')");
        //jexec.executeScript("window.open('about:blank')");
//        final String newWindow = driver
//                .getWindowHandles()
//                .stream()
//                .filter(wh -> !wh.contentEquals(initialTab))
//                .findFirst()
//                .get();
//        Thread.sleep(3000);
        //driver.getWindowHandles().forEach(wh -> System.out.println(wh));
        //driver.switchTo().window(newWindow);
        driver.get("https://jira.hillel.it/browse/AQA919-4");
        driver.manage().window().maximize();
        LoginPage.login(driver);
        Thread.sleep(1000);
//        String xpathAssignToMe = "//a[@id='assign-to-me']";
//        driver.findElement(By.xpath(xpathAssignToMe)).click();
//        String newName = "//span[@id='issue_summary_assignee_n.n.kopko']";
//        String xpathName = driver.findElement(By.xpath(newName)).getText().trim();
//        String xpathName2="Niko";
//        Assert.assertEquals(xpathName,xpathName2);
//        Thread.sleep(2000);
//        String xpathPop = "//div[@class='aui-message closeable aui-message-success aui-will-close']";
//        String xpathPopup = driver.findElement(By.xpath(xpathPop)).getText().trim();
//        String xpathPopup2="AQA919-4 has been assigned.";
//        Assert.assertEquals(xpathPopup,xpathPopup2);

        String xpathPriority = "//span[@id='priority-val']";
        driver.findElement(By.xpath(xpathPriority)).click();
        List<WebElement> innerBlockList = driver.findElements(By.xpath("//option[@class='imagebacked']"));
        System.out.println(innerBlockList.size());
        for (int i = 0; i < innerBlockList.size(); i++) {
            System.out.println(innerBlockList.get(i).getAttribute("innerText").trim());
        }
        driver.findElement(By.xpath("//span[@class='icon aui-ss-icon noloading drop-menu']")).click();
        Thread.sleep(2000);
        List<WebElement> lables = driver.findElements(By.xpath("//a[@class='lozenge']"));
        System.out.println(lables.size());
        for (int i = 0; i < lables.size(); i++) {
            System.out.println(lables.get(i).getText().trim());
        }

        String xpathLabel = "//div[@class='labels-wrap value editable-field inactive']";
        driver.findElement(By.xpath(xpathLabel)).click();
        String xpathLabelInput = "//textarea[@autocomplete='off']";
        WebElement inputFieldLabel = driver.findElement(By.xpath(xpathLabelInput));
        inputFieldLabel.sendKeys("HelloAmigo!!!");
        String xpathEnterLabel = "//a[@class='aui-list-item-link']";
        driver.findElement(By.xpath(xpathEnterLabel)).click();
        Thread.sleep(1000);
        String xpathEnterLabel2 = "//span[@class='aui-icon aui-icon-small aui-iconfont-success']";
        driver.findElement(By.xpath(xpathEnterLabel2)).click();
        Thread.sleep(2000);

        String xpathDescription = "//div[@id='description-val']";
        driver.findElement(By.xpath(xpathDescription)).click();
        String xpathText = "//li[@class='aui-nav-selected']//a[contains(text(),'Text')]";
        driver.findElement(By.xpath(xpathText)).click();
        String xpathDescriptionInput = "//textarea[@id='description']";
        driver.findElement(By.xpath(xpathDescriptionInput)).click();
        WebElement inputDescription = driver.findElement(By.xpath(xpathDescriptionInput));
        inputDescription.sendKeys("test complete");
        String xpathDescriptionSave = "//button[@title='Press Alt+s to submit this form']";
        driver.findElement(By.xpath(xpathDescriptionSave)).click();
        Thread.sleep(2000);

        String xpathRelease = "//span[@class='aui-icon aui-icon-large icon-sidebar-release aui-iconfont-ship']";
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.LEFT_CONTROL)
                .click(driver.findElement(By.xpath(xpathRelease)))
                .keyUp(Keys.LEFT_CONTROL)
                .build()
                .perform();

        ArrayList tabs2 = new ArrayList(driver.getWindowHandles());
        driver.switchTo().window((String) tabs2.get(1));

        Thread.sleep(2000);
        String xpathReleaseText = "//div[@class='aui-page-header-inner']/h1";
        String xpathReleaseText2 = driver.findElement(By.xpath(xpathReleaseText)).getAttribute("innerText").trim();
        System.out.println(xpathReleaseText2);
        String xpathReleaseTextTrue = "Releases";
        Assert.assertEquals(xpathReleaseText2, xpathReleaseTextTrue);

        String xpathManageV = "//a[@id='manage-versions']";
        String xpathManageV2 = driver.findElement(By.xpath(xpathManageV)).getAttribute("innerText").trim();
        System.out.println(xpathManageV2);
        String xpathManageVTrue = "Manage Versions";
        Assert.assertEquals(xpathManageV2, xpathManageVTrue);

        String url = driver.getCurrentUrl();
        String urlTrue = "https://jira.hillel.it/projects/AQA919?selectedItem=com.atlassian.jira.jira-projects-plugin:release-page";
        Assert.assertEquals(url, urlTrue);

        //Thread.sleep(3000);
        // driver.switchTo().window(initialTab);
        // driver.close();
        // driver.switchTo().window(wikiTabHandler);
    }
}