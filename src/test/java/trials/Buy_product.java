package trials;

import infra.Browser;
import infra.Browserfactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Buy_product {
    @Test
    public void byuProduct() throws InterruptedException {
        final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
        final JavascriptExecutor jexec = (JavascriptExecutor) driver;
        final String initialTab = driver.getWindowHandle();

        //login
        driver.manage().window().maximize();
        driver.get("https://bt.rozetka.com.ua/161581197/p161581197/");

        Thread.sleep(300);
        //купить продукт на странице продукта
        driver.findElement(By.xpath("//*[@id=\"price_container\"]//button[@class='btn-link-i']")).click();
        Thread.sleep(3000);

        driver.switchTo().activeElement();

        String productCount = driver.findElement(By.xpath("//input[@name=\"quantity\"]")).getAttribute("value");//get text
        Assert.assertEquals( productCount ,"1" );





        //driver.findElement(By.xpath("//*[@id='cart_block93306']")).click();

        driver.quit();

    }
}
