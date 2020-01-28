package infra;

public enum Browser {
    CHROME("chromedrivers/chromedriver"),
//    FIREFOX("marionettedrivers/marionettedriver");
    FIREFOX("firefoxdrivers/geckodriver");
    public final String driverPathInBin;

    private Browser(String p){
        driverPathInBin = p;
    }

}
