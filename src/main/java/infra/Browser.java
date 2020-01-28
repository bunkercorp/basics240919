package infra;

public enum Browser {
    CHROME("chromedrivers/chromedriver"),
    FIREFOX("geckodriver/geckodriver");

    public final String driverPathInBin;

    private Browser(String p){
        driverPathInBin = p;
    }

}
