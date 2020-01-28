package pages;

import infra.ConfigurationManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public final class IssuePage extends AbstractPage {
    private static final Pattern jiraIssuePattern = Pattern.compile("^[A-Z0-9]+-\\d+$");

    {
        selectors.put("jiraKeyLbl", By.xpath("//*[@id=\"key-val\"]"));
        selectors.put("currentAssigneeLbl", By.xpath("//*[@id=\"assignee-val\"]"));
        selectors.put("assignToMeLnk", By.xpath("//*[@id=\"assign-to-me\"]"));
    }


    private static IssuePage instance = new IssuePage();

    public static IssuePage instance() {
        if (instance == null)
            instance = new IssuePage();
        return instance;
    }

    public static IssuePage instance(WebDriver driver) {
        instance = (IssuePage) instance().with(driver);
        return instance;
    }

    @Override
    public ReturnResult visit(Object... pageSpecificArguments) {
        ReturnResult result;
        if (pageSpecificArguments.length != 1 || !pageSpecificArguments[0].getClass().equals(String.class))
            throw new IllegalArgumentException("IssuePage::visit expects exactly one argument of type String");
        else {
            String candidate = (String) pageSpecificArguments[0];
            if (!jiraIssuePattern.matcher(candidate).matches()) {
                throw new IllegalArgumentException(String.format("IssuePage::visit received \"%s\" which does not seem as a valid jira key", candidate));
            } else {
                String fullUrl = String.format("%s/browse/%s", ConfigurationManager.getInstance().getConfig("projectBaseUrl", String.class), candidate);
                storedDriver.get(fullUrl);
                result = isOnScreen();
            }
        }
        return result;
    }

    @Override
    public ReturnResult isOnScreen() {
        ReturnResult.Builder result = new ReturnResult.Builder();
        try {
            WebElement jiraKeyLbl = storedDriver.findElement(selectors.get("jiraKeyLbl"));
            result.setResult(jiraKeyLbl.getText());
        } catch (Throwable t) {
            result.addError(t);
        }
        return result.compose();
    }

    public ReturnResult getAssignee() {
        ReturnResult.Builder result = new ReturnResult.Builder();
        try {
            WebElement currentVal = storedDriver.findElement(selectors.get("currentAssigneeLbl"));
            result.setResult(currentVal.getText().contentEquals("Unassigned") ? null : currentVal.getText());
        } catch (Throwable t) {
            result.addError(t);
        }
        return result.compose();
    }

    public ReturnResult assignToMe() {
        ReturnResult.Builder result = new ReturnResult.Builder();

        boolean assignedAlready = getAssignee().getResult() != null;
        if (assignedAlready)
            result.addError(unassign());

        List<WebElement> assignToMeLinks = storedDriver.findElements(selectors.get("assignToMeLnk"));
        if (assignToMeLinks.size() == 1) {
            try {

            } catch (Throwable t) {
                result.addError(t);
            }

        } else if (assignToMeLinks.size() > 1)
            result.addError(String.format("Found %d assign to me links", assignToMeLinks.size()));
        return result.compose();
    }

    public ReturnResult unassign() {
        ReturnResult.Builder result = new ReturnResult.Builder();
        return result.compose();
    }

    public ReturnResult assignTo(String userFullName) {
        ReturnResult.Builder result = new ReturnResult.Builder();
        return result.compose();

    }

    public ReturnResult getDescription() {
        ReturnResult.Builder result = new ReturnResult.Builder();
        return result.compose();

    }

    public ReturnResult setDescription(String content, boolean append) {
        ReturnResult.Builder result = new ReturnResult.Builder();
        return result.compose();
    }

    public ReturnResult clearDescription(boolean save) {
        ReturnResult.Builder result = new ReturnResult.Builder();
        return result.compose();

    }

    public ReturnResult setLabel(String label) {
        ReturnResult.Builder result = new ReturnResult.Builder();
        return result.compose();
    }

    public ReturnResult setLabels(Collection<String> labels) {
        ReturnResult.Builder result = new ReturnResult.Builder();


        return result.compose();
    }

}
