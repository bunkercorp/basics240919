package trials;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    public void login (String jiraUser, String jiraPwd, WebDriver driver){
        driver.findElement(By.xpath("//*[@id=\"login-form-username\"]")).sendKeys(jiraUser);
        driver.findElement(By.xpath("//*[@id=\"login-form-password\"]")).sendKeys(jiraPwd);
        driver.findElement(By.xpath("//*[@id=\"login-form-submit\"]")).click();
    }
}
