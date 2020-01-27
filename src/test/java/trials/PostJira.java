package trials;

import http.jira.JiraIssuePoster;
import infra.Browser;
import infra.BrowserFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.IssuePage;
import pages.LoginPage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

public class PostJira {

    @Test
    public void postJira() throws IOException, InterruptedException {
        final String projectKey = "AQA919";
        final String summary = String.format("This issue has been automatically created at %s", new Date().toString());

        JiraIssuePoster.JiraIssue postedIssue = new JiraIssuePoster()
                .forProject(projectKey)
                .label("lorem")
                .ofType("Bug")
                .summary(summary)
                .create();

        WebDriver driver = BrowserFactory.getDriver(Browser.CHROME);
        IssuePage.instance(driver).visit(postedIssue.key);
        LoginPage.instance(driver).login();
        String actualJiraKey = (String) IssuePage.instance().isOnScreen().getResult();
        Assert.assertEquals(actualJiraKey, postedIssue.key);
        driver.quit();
    }


}
