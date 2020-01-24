package trials;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Labels {
    public void printLabels(WebDriver driver){
        WebDriverWait waiting = new WebDriverWait(driver, 10);
        List<WebElement> labelList = driver.findElements(By.className("lozenge"));
        System.out.println("LABELS VALUES:");
        for (WebElement elem : labelList){
            System.out.println(elem.getText());
        }
    }


    public void addLabel(WebDriver driver, String labelName){
        WebDriverWait waiting = new WebDriverWait(driver, 10);
        Actions labelsAct = new Actions(driver);
        WebElement LabelField = driver.findElement(By.xpath("//ul [@id='labels-68064-value']"));
        labelsAct.moveToElement(LabelField).build().perform();//эмулируем наведение курсором
        waiting.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[@id='wrap-labels']/div/span")))
                .click();
        WebElement labelElement = driver
                .findElement(By.xpath("//textarea[@id='labels-textarea']"));
        waiting.until(ExpectedConditions.visibilityOf(labelElement));
        labelElement.sendKeys("AutoLabel");
        labelElement.submit();
    }
}
