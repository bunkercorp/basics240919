package infra;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {


//    final String username = "";
//    final String password = "";

    public static void login ( WebDriver driver){
        final String username = System.getProperty("jiraUser");
        final String password = System.getProperty("jiraPwd");
        driver.findElement(By.xpath("//*[@id=\"login-form-username\"]")).sendKeys(username);
        driver.findElement(By.xpath("//*[@id=\"login-form-password\"]")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"login-form-submit\"]")).click();
    }


}
