package trials;

import http.jira.JiraIssuePoster;
import infra.Browser;
import infra.BrowserFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.IssuePage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

public class PostJira {

    WebDriver driver = null;

    @BeforeClass
    public void beforeSuite() throws MalformedURLException {
        driver = BrowserFactory.getDriver(Browser.CHROME);
    }

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

// FIXME NPE
        ((IssuePage) new IssuePage()
                .with(driver))
                .visit(postedIssue.key);


        Thread.sleep(5000);
        driver.quit();

    }
}
