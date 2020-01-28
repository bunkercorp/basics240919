package infra;

public enum Browser {
    CHROME("chromedrivers/chromedriver"),
    FIREFOX("chromedrivers/geckodriver");

    public final String driverPathInBin;

    private Browser(String p){
        driverPathInBin = p;
    }

}
