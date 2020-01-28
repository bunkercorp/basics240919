package trials;

import infra.Browser;
import infra.BrowserFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Buy_product {
    String targetProduct = "Блендер Hilton SMS 8143";
    float productPrice = 999;
    @Test
    public void byuProduct() throws InterruptedException {
        final WebDriver driver = BrowserFactory.getDriver();
        final JavascriptExecutor jexec = (JavascriptExecutor) driver;
        final String initialTab = driver.getWindowHandle();

        //login
        driver.manage().window().maximize();
        driver.get("https://bt.rozetka.com.ua/161581197/p161581197/");

        Thread.sleep(300);
        //купить продукт на странице продукта
        driver.findElement(By.xpath("//*[@id='price_container']//button[@class='btn-link-i']")).click();
        Thread.sleep(3000);

        driver.switchTo().activeElement();
// убедиться что вкорзине 1 продукт
        String productCount = driver.findElement(By.xpath("//input[@name=\"quantity\"]")).getAttribute("value");//get text
        Assert.assertEquals( productCount ,"1" );
//        float count = Float.parseFloat(productCount);

// Убедиться, что продукт имеет ожидаемое название б цену и сумму

        //name
        String productName = driver.findElement(By.xpath("//div[@class=\"cart-i-title\"]/a")).getText();
        Assert.assertEquals(productName, targetProduct );
//        //price
//        String strExpectedPrice = driver.findElement(By.xpath("//span[@class='cart-uah']")).getText();
//        String[] s2 = strExpectedPrice.split("\\D+");
//        String result = "";
//        for (int i = 1; i < s2.length; i++) {
//            if (s2[i].equals(null))
//                result += s2[i];
//        }
//        float expectedPrice = Float.parseFloat(result);
//        Assert.assertEquals(productPrice,expectedPrice);
//        //cart sum
//float sum = count * expectedPrice;
//        String strExpectedSum = driver.findElement(By.xpath("//*[@id=\"cart_payment_info\"]/div/span[2]/span[1]")).getText();
//        float ExpectedSum = Float.parseFloat(strExpectedSum);
//        Assert.assertEquals(sum,ExpectedSum);
//
//
//String productPrice = driver.findElement(By.xpath("class=\"cart-uah\"")).getText();

        //Нажать на Продолжить покупки
        WebElement continueShoppingBtn = driver.findElement(By.xpath("//*[@id=\"cart-popup\"]/div[2]/div[2]/div[3]/span/span/a"));
        continueShoppingBtn.click();
        Thread.sleep(3000);


        //Убедиться, что индикатор корзины показывает нужное количество продуктов
        String hubCount = driver.findElement(By.xpath("//*[@id=\"cart_popup_header\"]/div[2]//span[2]")).getText();
        Assert.assertEquals(productCount, hubCount );

        //Нажать на корзину

        driver.findElement(By.xpath("//*[@id=\"cart_popup_header\"]/div[2]")).click();
        Thread.sleep(3000);


        //нажать на плюс
        WebElement plusButton = driver.findElement(By.xpath("//*[@id=\"cart-popup\"]//img[@class=\"cart-amount-plus-icon sprite\"]"));
        plusButton.click();

        Thread.sleep(3000);



        driver.quit();

    }
}
