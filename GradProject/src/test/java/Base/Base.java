package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DriverFactory;
import utils.PropertyReader;

public class Base {
    protected WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = DriverFactory.initializeDriver();
        driver.get(PropertyReader.getProperty("base.url", "chrome"));
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
