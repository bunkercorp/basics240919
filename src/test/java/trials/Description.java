package trials;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Description {
    public void addDescription(WebDriver driver, String description) throws InterruptedException {
        String currentWindowHandle = driver.getWindowHandle();
        WebElement formWrapper = driver.findElement(By.id("description-val"));
        formWrapper.click();
//        Thread.sleep(300);
        driver.switchTo().frame(new WebDriverWait(driver,5)
            .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='description-wiki-edit']//iframe"))));
        Thread.sleep(300);
        driver.findElement(By.xpath("/html/body/p")).sendKeys(description);
        Thread.sleep(500);

        driver.switchTo().window(currentWindowHandle);

        formWrapper.findElement(By.xpath("//button[@type='submit' and @class='aui-button aui-button-primary submit']"))
                .click();

    }

    public String getDescription(WebDriver driver){
            WebElement descriptionElem = driver.findElement(By.xpath("//div[@id='description-val']/div/p"));
            return descriptionElem.getText();
    }
}
