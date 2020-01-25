package infra;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {


    public static void login (WebDriver driver){


        driver.findElement(By.xpath("//*[@id=\"login-form-username\"] ")).sendKeys(ConfigManager.getInstance().getUsername());

        driver.findElement(By.xpath("//*[@id=\"login-form-password\"]")).sendKeys(ConfigManager.getInstance().getPassword());

        driver.findElement(By.xpath("//*[@id=\"login-form-submit\"]")).click();
    }
}
