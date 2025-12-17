// DriverFactory.java
package Drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.SafariDriver;
import utils.PropertyReader;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver initializeDriver() {
        if (driver.get() != null) {
            return driver.get();
        }

        String browser = System.getProperty("browser", PropertyReader.getProperty("browser")).toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", PropertyReader.getProperty("headless")));

        System.out.println(" Launching browser: " + browser + " | Headless: " + headless);

        switch (browser) {
            case "chrome": {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                if (headless) chromeOptions.addArguments("--headless=new");
                driver.set(new ChromeDriver(chromeOptions));
                break;
            }
            case "edge": {
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");
                if (headless) edgeOptions.addArguments("--headless=new");
                driver.set(new EdgeDriver(edgeOptions));
                break;
            }
            case "safari":
                driver.set(new SafariDriver());
                break;
            default:
                throw new IllegalArgumentException(" Unsupported browser: " + browser);
        }

        try {
            driver.get().manage().window().maximize();
        } catch (Exception e) {
            // ignore maximize for headless or unsupported platforms
        }
        return driver.get();
    }

    public static void quitDriver() {
        WebDriver wd = driver.get();
        if (wd != null) {
            try {
                wd.quit();
            } catch (Exception ignored) {}
            driver.remove();
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }
}
