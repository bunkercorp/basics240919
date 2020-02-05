package trials;

import infra.Browser;
import infra.Browserfactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Rozetka {

    @Test
    public void firstTest() throws InterruptedException {
        // final WebDriver driver = Browserfactory.getDriver(Browser.CHROME);
      /* ну вот... мы задавали имя браузера как параметр запуска мавен таски,
      который пробрасывался дженкинсом, и который считывался в BrowserFactory,
         то есть, нам уже не нужно было вызывать getDriver с параметром, BrowserFactory уже знала, что отдавать.
         Спишу на невнимательность =)
         */
        final WebDriver driver = Browserfactory.getDriver(Browser.FIREFOX);
        final JavascriptExecutor jexec = (JavascriptExecutor) driver;
        final String initialTab = driver.getWindowHandle();
        String name = "Погружной блендер Hilton SMS-8143";

        driver.get("https://rozetka.com.ua/");

        String xpathSearchInput = "//input[@class='search-form__input ng-untouched ng-pristine ng-valid']";
        driver.findElement(By.xpath(xpathSearchInput)).click();
        WebElement inputSearch = driver.findElement(By.xpath(xpathSearchInput));
        inputSearch.sendKeys(name);
        String xpathSearchClick = "//button[@class='button button_color_green button_size_medium search-form__submit']";
        driver.findElement(By.xpath(xpathSearchClick)).click();
        // что бы это могло значить? если охота подождать элемент, то ExpectedConditions в помощь же =)
        Thread.sleep(3000);

        String xpathGetName = driver.findElement(By.xpath("//span[@class='goods-tile__title']")).getAttribute("innerText").trim();
        String currentName = name;
        Assert.assertEquals(xpathGetName, currentName);
/* какой грязный хак. Во-первых, String.format("//img[@title='%s ']", name) не?
во вторых, задача стояла именно перебрать все карточки продуктов в поисках нужного, а не бить нужный точечно.
*/
        String xpathProduct = "//img[@title='Погружной блендер Hilton SMS-8143 ']";
        driver.findElement(By.xpath(xpathProduct)).click();
      // ExpectedConditions =)
        Thread.sleep(3000);

        String xpathGetNameProduct = driver.findElement(By.xpath("//h1[@class='product__title']")).getAttribute("innerText").trim();
        String currentNameProduct = name;
        Assert.assertEquals(xpathGetNameProduct, currentNameProduct);

        String xpathPrice2 = "//p[@class='product-prices__big product-prices__big_color_red']";
        // .getText() ? хотя, тут как угодно.
        String xpathPrice = driver.findElement(By.xpath(xpathPrice2)).getAttribute("innerText").trim();
        xpathPrice = xpathPrice.substring(0, xpathPrice.length() - 1);
        float price = Float.parseFloat(xpathPrice);

        String xpathBuy = "//button[@class='buy-button button button_with_icon button_color_green button_size_large']";
        driver.findElement(By.xpath(xpathBuy)).click();
        Thread.sleep(3000);

        String xpathGetNamberProduct = driver.findElement(By.xpath("//input[@class='cart-modal__calc-input']")).getAttribute("value").trim();
        String currentNamberProduct = "1";
        Assert.assertEquals(xpathGetNamberProduct, currentNamberProduct);

        String xpathNameBuyPage = driver.findElement(By.xpath("//a[@class='cart-modal__title']")).getAttribute("innerText").trim();
        String currentNameBuyPage = name;
        Assert.assertEquals(xpathNameBuyPage, currentNameBuyPage);
        String xpathPriceBuyPage = driver.findElement(By.xpath("//div[@class='cart-modal__coast cart-modal__coast_type_transparent']/span[@class='cart-modal__coast-digits']")).getAttribute("innerText").trim();
        Assert.assertEquals(xpathPriceBuyPage, xpathPrice);
        String xpathSumBuyPage = driver.findElement(By.xpath("//span[@class='cart-modal__check-digits']")).getAttribute("innerText").trim();
        Assert.assertEquals(xpathSumBuyPage, xpathPrice);

        String xpathContinueBuy = "//a[@class='button button_color_gray cart-modal__bottom-continue']";
        driver.findElement(By.xpath(xpathContinueBuy)).click();

        String xpathCity = "//a[@class='header-cities__link link-dashed']";
        driver.findElement(By.xpath(xpathCity)).click();
// захват элемента по тексту - ататай. По-хорошему ,следует захватить все кнопки с городами и в цикле определить нужную. 
        driver.findElement(By.xpath("//a[text()=' Одесса ']")).click();
        Thread.sleep(2000);

        String xpathGetCountProduct = driver.findElement(By.xpath("//span[@class='header-actions__button-counter']")).getAttribute("innerText").trim();
        String currentCountProduct = "1";
        Assert.assertEquals(xpathGetCountProduct, currentCountProduct);

        String xpathBasket = "//a[@class='header-actions__button header-actions__button_type_basket header-actions__button_state_active']";
        driver.findElement(By.xpath(xpathBasket)).click();
        Thread.sleep(3000);

        // окей, украинская локализация? =)
        String xpathPlus = "//button[@aria-label='Добавить ещё один товар']";
        driver.findElement(By.xpath(xpathPlus)).click();
        Thread.sleep(2000);

        String xpathSum = driver.findElement(By.xpath("//div[@class='cart-modal__coast cart-modal__coast_type_transparent']/span[@class='cart-modal__coast-digits']")).getAttribute("innerText").trim();
        String currentSum = driver.findElement(By.xpath("//span[@class='cart-modal__check-digits']")).getAttribute("innerText").trim();
        Assert.assertEquals(xpathSum, currentSum);

        String xpathOrder = "//a[@class='button button_color_green cart-modal__check-button']";
        driver.findElement(By.xpath(xpathOrder)).click();
        Thread.sleep(3000);

        String xpathOrderPage = driver.findElement(By.xpath("//h2[@class='check-title']")).getAttribute("innerText").trim();
        String currentOrderPage = "Оформление заказа";
        Assert.assertEquals(xpathOrderPage, currentOrderPage);
    }
}
