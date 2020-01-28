package trials;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Description {
    public void addDescription(WebDriver driver, String description) throws InterruptedException {
        String currentWindowHandle = driver.getWindowHandle();
        WebDriverWait wait = new WebDriverWait(driver,10);

        WebElement formWrapper = driver.findElement(By.id("description-val"));
        formWrapper.click();
        driver.switchTo().frame(wait
            .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='wiki-edit-content']//iframe"))));

        driver.findElement(By.xpath("/html/body")).sendKeys(description);

//        driver.switchTo().window(currentWindowHandle);
        driver.switchTo().parentFrame();
        formWrapper.findElement(By.xpath("//button[@type='submit' and @class='aui-button aui-button-primary submit']"))
                .click();

    }

    public String getDescription(WebDriver driver){
            WebElement descriptionElem = driver.findElement(By.xpath("//div[@id='description-val']/div/p"));
            return descriptionElem.getText();
    }
}
