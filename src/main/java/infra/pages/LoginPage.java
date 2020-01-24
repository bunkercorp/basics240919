package infra.pages;

import infra.ConfigurationManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage  {
    public static void login(WebDriver driver) {
        final String username = ConfigurationManager.getInstance().getLogin();
        final String password = ConfigurationManager.getInstance().getPassword();

        driver.findElement(By.xpath("//*[@id=\"login-form-username\"]")).sendKeys(username);
        driver.findElement(By.xpath("//*[@id=\"login-form-password\"]")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"login-form-submit\"]")).click();
    }


}
