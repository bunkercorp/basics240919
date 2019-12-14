package trials;

import infra.Browser;
import infra.Browserfactory;
import org.testng.annotations.Test;

public class SomeClass {
    @Test
    public void firstTest()  {
        Browserfactory.getDriver(Browser.CHROME);
    }
}