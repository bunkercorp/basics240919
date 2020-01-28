package pages;

import infra.ConfigurationManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

public final class LoginPage extends pages.AbstractPage {

    {
        selectors.put("unameInput", By.xpath("//*[@id=\"login-form-username\"]"));
        selectors.put("pwdInput", By.xpath("//*[@id=\"login-form-password\"]"));
        selectors.put("submitBtn", By.xpath("//*[@id=\"login-form-submit\"]"));
    }

    private static LoginPage instance;

    public static LoginPage instance() {
        if (instance == null)
            instance = new LoginPage();
        return instance;
    }

    public static LoginPage instance(WebDriver driver) {
        instance = instance();
        return (LoginPage) instance.with(driver);
    }

    public void login() {
        final String username = ConfigurationManager.getInstance().getConfig("jiraUser", String.class);
        final String password = ConfigurationManager.getInstance().getConfig("jiraPwd", String.class);

        storedDriver.findElement(selectors.get("unameInput")).sendKeys(username);
        storedDriver.findElement(selectors.get("pwdInput")).sendKeys(password);
        storedDriver.findElement(selectors.get("submitBtn")).click();
    }


    @Override
    public ReturnResult visit(Object... pageSpecificArguments) {
        return null;
    }

    @Override
    public ReturnResult isOnScreen() {
        final ReturnResult.Builder result = new ReturnResult.Builder();
        selectors.forEach((selectorName, selectorBy) -> {
            boolean isElementThere = storedDriver.findElements(selectorBy).size() == 1;
            result.setResult(((Boolean) result.getResult()) && isElementThere);
            if (!isElementThere)
                result.addError(String.format("%s element cannot be found on login page", selectorName));
        });
        return result.compose();
    }
}
