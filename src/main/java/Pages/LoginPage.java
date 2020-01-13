package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    public static void login (WebDriver driver){
        String jiraUser = System.getProperty("jirauser");
        String jiraPwd = System.getProperty("jirapwd");

        String loginFieldXpath ="//input[@id='login-form-username']";
        WebElement loginField = driver.findElement(By.xpath(loginFieldXpath));
        loginField.sendKeys(jiraUser);

        String passFieldXpath = "//input[@id='login-form-password']";
        WebElement passwField = driver.findElement(By.xpath(passFieldXpath));
        passwField.sendKeys(jiraPwd);

        String loginBtnXpath = "//input[@id='login-form-submit']";
        WebElement loginBtn = driver.findElement(By.xpath(loginBtnXpath));
        loginBtn.click();

    }
}
