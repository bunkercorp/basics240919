package trials;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

public class ReleasesPage {
    public String openReleasePageNewTab(WebDriver driver){
        String openAtNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
        driver.findElement(By.linkText("Releases")).sendKeys(openAtNewTab);

        ArrayList browserTabs = new ArrayList(driver.getWindowHandles());
        driver.switchTo().window((String) browserTabs.get(1));

        WebDriverWait waiting = new WebDriverWait(driver, 5);

        WebElement releaseGetText = waiting.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//div[@class='aui-page-header-inner']/h1")));
        return releaseGetText.getText();
    }
    public String checkPresenceManageButton(WebDriver driver){

        String manageVersionButtonXpath = "//a[@id='manage-versions' and @class='aui-button']";
        return driver.findElement(By.xpath(manageVersionButtonXpath)).getText();
//        return manageVersionButton;

    }
}
