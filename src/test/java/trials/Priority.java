package trials;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Priority {
    public void printPriorityList(WebDriver driver) throws InterruptedException {

        driver.findElement(By.xpath("//*[@id='priority-val']")).click();
        WebDriverWait waiting = new WebDriverWait(driver, 10);
        waiting.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//*[@id='priority-val']")))
                .getText();

        Thread.sleep(200);
        List<WebElement> priorityValues = driver
                .findElements(By.xpath("//*[@id='priority-suggestions']/div/ul/li/a"));
        System.out.println("PRIORITY VALUES:");
        for (WebElement priority : priorityValues) {
            System.out.println(priority.getText());
        }
        waiting.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[@id='priority-single-select']/span")))
                .click();
        waiting.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//*[@id='priority-form']/div[2]/button[1]")))
                .click();
        Thread.sleep(200);
    }
}
