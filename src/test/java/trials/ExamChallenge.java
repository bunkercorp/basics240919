package trials;

import infra.BrowserFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExamChallenge {

    private static final String searchtext = "Блендер Hilton SMS 8143";
    private static final String searchFormCommon = "//form";

    private static final By searchInput = By.xpath(String.format("%s//input[@name='search']", searchFormCommon));
    private static final By searchBtn = By.xpath(String.format("%s/button", searchFormCommon));

    private static final By srpItems = By.xpath("//app-search-goods/ul/li");
    private static final By srpItemLink = By.xpath("//a[@class='goods-tile__heading']/span");

    private static final By pdpHeader = By.xpath("//h1[@class='product__title']");
    private static final By pdpBuyBtn = By.xpath("//app-buy-button//button//span");
    private static final By pdpBestPrice = By.xpath("//div[@class='product-trade']//p");

    private final static Pattern uahPattern = Pattern.compile("(.*?)грн");

    private static final By cartItems = By.xpath("//cart-content//ul/li");
    private static final By cartitemLink = By.xpath("//div[@class='cart-modal__content']/a");
    private static final By cartitemSubtotal = By.xpath("//div[@class='cart-modal__content']//div[@class='cart-modal__sum']");
    private final static By cartContinueShoppingBtn = By.xpath("//div[@class='cart-modal__bottom']/a");
    private final static By cartItemCalcInputs = By.xpath("//div[@class='cart-modal__calc']//button");
    private final static By cartTotalsLbl = By.xpath("//div[@class='cart-modal__bottom']/div/div/span[2]");
    private final static By cartGoCheckoutBtn = By.xpath("//div[@class='cart-modal__bottom']/div/a");


    private static final By headerCurrentCity = By.xpath("//div[@class='header-cities']/a");
    private static final By cityItemInModal = By.xpath("//single-modal-window//ul/li/a");
    private static final By headerCartBtn = By.xpath("//header//ul[@class='header-actions']/li[5]/div/div/a/span");


    private static WebDriverWait waitForElements = null;

    private static String dropSpaces(String src) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(src.split(" ")).forEach(e -> {
            if (e.length() > 0)
                sb.append(e);
        });
        return sb.toString();
    }

    private static void checkCart(float expectedSubtotal, float expectedTotal, WebDriver driver) {
        List<WebElement> actualCartItems = waitForElements.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cartItems));
        Assert.assertEquals(actualCartItems.size(), 1, "Expected the cart to only contain one item");
        final String actualCartItemCaption = actualCartItems.get(0).findElement(cartitemLink).getText();
        Assert.assertEquals(actualCartItemCaption, searchtext);
        Matcher subtotalMatcher = uahPattern.matcher(dropSpaces(actualCartItems.get(0).findElement(cartitemSubtotal).getText().split("\n")[1].trim()));
        subtotalMatcher.find();
        float actualItemSubtotal = Float.parseFloat(subtotalMatcher.group(1));
        Assert.assertEquals(actualItemSubtotal, expectedSubtotal);
        Matcher totalMatcher = uahPattern.matcher(dropSpaces(waitForElements.until(ExpectedConditions.presenceOfElementLocated(cartTotalsLbl)).getText().trim()));
        totalMatcher.find();
        float actualCartTotal = Float.parseFloat(totalMatcher.group(1));
        Assert.assertEquals(actualCartTotal, expectedTotal);
    }

    @Test
    public static void examChallenge() throws InterruptedException {
        WebDriver driver = BrowserFactory.getDriver();
        driver.get("https://rozetka.com.ua");

        waitForElements = new WebDriverWait(driver, 4);
        waitForElements.until(ExpectedConditions.presenceOfElementLocated(searchInput)).sendKeys(searchtext);
        waitForElements.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
        List<WebElement> actualSrpItems = waitForElements.until(ExpectedConditions.presenceOfAllElementsLocatedBy(srpItems));

        int targetSrpItemIndex = -1;
        WebElement srpItemToClick = null;
        for (int i = 0; i < actualSrpItems.size(); i += 1) {
            srpItemToClick = actualSrpItems.get(i).findElement(srpItemLink);
            final String srpItemName = srpItemToClick.getText().trim();
            if (srpItemName.contentEquals(searchtext)) {
                targetSrpItemIndex = i;
                break;
            }
        }
        Assert.assertEquals(targetSrpItemIndex, 0, "Expected searched item to be the first item in the list");
        srpItemToClick.click();
        String actualPDPCaption = waitForElements.until(ExpectedConditions.presenceOfElementLocated(pdpHeader)).getText().trim();
        Assert.assertEquals(actualPDPCaption, searchtext);
        final float actualPrice = Float.parseFloat(driver.findElement(pdpBestPrice).getText().replace("₴", "").trim());
        try {
            waitForElements.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[@class='exponea-close-link']")))
                    .stream().findFirst().ifPresent(WebElement::click);
        } catch (Throwable ignored) {
        }
        waitForElements.until(ExpectedConditions.presenceOfElementLocated(pdpBuyBtn)).click();
        checkCart(actualPrice, actualPrice, driver);
        waitForElements.until(ExpectedConditions.presenceOfElementLocated(cartContinueShoppingBtn)).click();
        waitForElements.until(ExpectedConditions.presenceOfElementLocated(headerCurrentCity)).click();
        List<WebElement> cities = waitForElements.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cityItemInModal));
        boolean clicked = false;

        for (WebElement cityLink : cities) {
            if (cityLink.getText().contentEquals("Одесса")) {
                clicked = true;
                cityLink.click();
                break;
            }
        }
        Assert.assertTrue(clicked, "Not found Odessa in list of cities");
        WebElement cartBtnElem = waitForElements.until(ExpectedConditions.presenceOfElementLocated(headerCartBtn));
        int actualItemsInCart = Integer.parseInt(cartBtnElem.getText());
        Assert.assertEquals(actualItemsInCart, 1);
        cartBtnElem.click();
        checkCart(actualPrice, actualPrice, driver);
       WebElement plusBtn = waitForElements.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cartItemCalcInputs)).get(1);
       System.out.println(plusBtn.getTagName());
       plusBtn.click();
        Thread.sleep(1500);
        checkCart(actualPrice * 2, actualPrice * 2, driver);
        driver.findElement(cartGoCheckoutBtn).click();
    }
}
