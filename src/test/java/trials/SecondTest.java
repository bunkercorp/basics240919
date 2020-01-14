package trials;

import infra.Browser;
import infra.Browserfactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

public class SecondTest {

    @Test

    private void getPriority() throws InterruptedException {
        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);

        driver.get("https://jira.hillel.it/browse/AQA919-8");
        LoginPage log = new LoginPage();
        log.login(driver);

        driver.findElement(By.xpath("//*[@id=\"priority-val\"]")).click();

        Thread.sleep(20);

       List<WebElement> webElementList = driver.findElements(By.xpath("//*[@id=\"priority-suggestions\"]/div/ul/li"));
        for (WebElement text: webElementList){
           System.out.println(text.getText());
        }

   //    driver.findElement(By.xpath("//*[@id=\"priority-single-select\"]/span")).click();

      //  Thread.sleep(5);


      //  List <WebElement> webElements = driver.findElements(By.xpath("//*[@id=\"labels-68067-value\"]/li/a"));

      //  for (WebElement text: webElements){
       //     System.out.println(text.getText());

    //    }
     //   driver.findElement(By.xpath("//*[@id=\"wrap-labels\"]/div/")).click();

      //  driver.close();





       // System.out.println(webElementList.size());


      //  for (int i = 0; i < webElementList.size(); i++) {
        //    System.out.println(webElementList.get(i).getText());


        }
    }



