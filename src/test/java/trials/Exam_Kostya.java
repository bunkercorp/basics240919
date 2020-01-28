package trials;

import infra.BrowserFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;


public class Exam_Kostya {

    @Test
    public void firstTest() throws InterruptedException {

            final WebDriver driver = BrowserFactory.getDriver();
            driver.get("https://rozetka.com.ua/");

            driver.findElement
                    (By.xpath("//input[@placeholder='Я ищу...']"))
                    .sendKeys("Блендер Hilton SMS 8143");

            driver.findElement
                    (By.xpath("//button[@class='button button_color_green button_size_medium search-form__submit']"))
                    .click();
            Thread.sleep(2000);

            String result = driver.findElement(By.xpath("/html/body/app-root/div/div[1]/app-rz-search/div/main/search-result/div[2]/section/app-search-goods/ul/li[1]/app-goods-tile-default/div/div[2]/a[2]/span")).getText();
            Assert.assertEquals(result, "Блендер Hilton SMS 8143");
            Thread.sleep(2000);

            driver.findElement
                    (By.xpath("//app-root//app-rz-search//search-result//section[@class='content content_type_catalog js_content']/app-search-goods/ul[@class='catalog-grid']/li[1]/app-goods-tile-default//div[@class='goods-tile__inner']/a[1]"))
                    .click();

            WebDriverWait wait = new WebDriverWait(driver, 20);
            wait.until(ExpectedConditions.visibilityOfElementLocated
                    (By.className("product__title")))
                    .getText();

            Assert.assertEquals("Блендер Hilton SMS 8143", "Блендер Hilton SMS 8143");

//            Thread.sleep(3000);
//            String StrActualSum = driver.findElement(By.xpath("//p[@class='product-prices__big product-prices__big_color_red']")).getText();
//            float actualSum = Float.parseFloat(StrActualSum);
//            Thread.sleep(3000);

            //Нажать "Купить"
            WebElement buyButton = driver.findElement(By.cssSelector("body > app-root > div > div:nth-child(2) > app-rz-product > div > product-tab-main > div:nth-child(1) > div.product-about.pos-fix > div.product-about__right > product-main-info > div > div > app-buy-button > button"));
            wait.until(ExpectedConditions.visibilityOf(buyButton));
            buyButton.click();
            Thread.sleep(2000);

            //Убедиться, что в корзине ровно один продукт(а точнее кол-во этого элемента = 1)
            String currentWindow = driver.getWindowHandle();

            driver.switchTo().activeElement();
            String productCount = driver.findElement(By.xpath("//input[@class='cart-modal__calc-input']")).getAttribute("value");//get text
            Assert.assertEquals( productCount ,"1" );

        /*Убедиться, что единственный продукт имеет ожидаемое название,
        ожидаемую цену, корзина имет ожидаемую сумму*/
            //name
            String expectedName = "Блендер Hilton SMS 8143";
            String actualName =driver.findElement(By.cssSelector("body > app-root > div > div:nth-child(2) > div.app-rz-common > cart-modal > modal-window > div > div > div > cart-content > div:nth-child(1) > ul > li > div > div > a")).getText() ;
            Assert.assertEquals(expectedName,actualName);
            //price
            float expectedSum = 999;
            String StrActualSum = driver.findElement(By.cssSelector("body > app-root > div > div:nth-child(2) > div.app-rz-common > cart-modal > modal-window > div > div > div > cart-content > div:nth-child(1) > ul > li:nth-child(1) > div > div > div.cart-modal__item-flex > div.cart-modal__sum > div > span.cart-modal__coast-digits")).getText();
            float actualSum = Float.parseFloat(StrActualSum);
            Assert.assertEquals(expectedSum,actualSum);
            //cart sum
            String StrActualCheckoutSum = driver.findElement(By.cssSelector("body > app-root > div > div:nth-child(2) > div.app-rz-common > cart-modal > modal-window > div > div > div > cart-content > div:nth-child(1) > div > div > div > span:nth-child(2) > span.cart-modal__check-digits")).getText();
            float actualCheckoutSum = Float.parseFloat(StrActualCheckoutSum);
            float expectedCheckoutSum = 999;
            Assert.assertEquals(actualCheckoutSum,expectedCheckoutSum);
            Thread.sleep(1000);

            driver.switchTo().window(currentWindow);
            //Нажать "Продолжить покупки"
            WebElement continueShoppingBtn = driver.findElement(By.xpath("//a[@class='button button_color_gray cart-modal__bottom-continue']"));
            continueShoppingBtn.click();


            Thread.sleep(2000);

            driver.findElement(By.xpath("/html/body/app-root/div/div[1]/div[1]/header/div/div[1]/div[2]/div/a")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("/html/body/app-root/single-modal-window/div[2]/div[2]/common-city/ul/li[2]/a")).click();

            String result1 = driver.findElement(By.xpath("/html/body/app-root/div/div[1]/div[1]/header/div/div[2]/ul/li[5]/div/div/a/span")).getText();
            Assert.assertEquals(result1, "1");
            //click on basket
            driver.findElement(By.xpath("/html/body/app-root/div/div[1]/div[1]/header/div/div[2]/ul/li[5]/div/div/a")).click();


            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='app-rz-common']//button[2]"))).click();
            Thread.sleep(1500);
            //step 15; verify sum of 2 elements
            float expectedPrice2 = 1998;
            String StrActualSum2 = driver.findElement(By.cssSelector("body > app-root > div > div:nth-child(2) > div.app-rz-common > cart-modal > modal-window > div > div > div > cart-content > div:nth-child(1) > ul > li > div > div > div.cart-modal__item-flex > div.cart-modal__sum > div > span.cart-modal__coast-digits")).getText();
            System.out.println(StrActualSum2);
            float actualSum2 = Float.parseFloat(StrActualSum2);
            Assert.assertEquals(expectedPrice2,actualSum2);

            Thread.sleep(1000);
            //   [cart sum*2]
            String StrActualCheckoutSum2 = driver.findElement(By.cssSelector("body > app-root > div > div:nth-child(2) > div.app-rz-common > cart-modal > modal-window > div > div > div > cart-content > div:nth-child(1) > div > div > div > span:nth-child(2) > span.cart-modal__check-digits")).getText();
            float actualCheckoutSum2 = Float.parseFloat(StrActualCheckoutSum2);
            float expectedCheckoutSum2 = 999*2;
            Assert.assertEquals(actualCheckoutSum2,expectedCheckoutSum2);

         //   driver.quit();
        }


        }
