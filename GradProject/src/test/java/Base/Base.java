package Base;

import io.qameta.allure.Step;
import listeners.TestListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import Drivers.DriverFactory;
import utils.PropertyReader;



@Listeners(TestListener.class)
public class Base {

    protected static final Logger logger = LogManager.getLogger(Base.class);
    protected WebDriver driver;

    /**
     * Runs once before all tests in the test class.
     * Use for one-time setup that doesn't need to be repeated.
     */
    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        logger.info("========================================");
        logger.info("Starting Test Class: {}", this.getClass().getSimpleName());
        logger.info("========================================");
    }

    /**
     * Runs before each test method.
     * Initializes WebDriver and navigates to base URL.
     */
    @BeforeMethod(alwaysRun = true)
    @Step("Setup: Initialize WebDriver and navigate to base URL")
    public void setup() {
        logger.info("Setting up test environment...");

        // Initialize driver
        driver = DriverFactory.initializeDriver();
        logger.info("WebDriver initialized successfully");

        // Navigate to base URL
        String baseUrl = PropertyReader.getProperty("base.url");
        logger.info("Navigating to base URL: {}", baseUrl);
        driver.get(baseUrl);
        logger.info("Navigation completed");
    }

    /**
     * Runs after each test method.
     * Quits WebDriver to clean up resources.
     *
     * IMPORTANT: Changed from @AfterClass to @AfterMethod for proper cleanup
     * This ensures driver is quit after EACH test, not just after all tests.
     */
    @AfterMethod(alwaysRun = true)
    @Step("Teardown: Quit WebDriver")
    public void tearDown() {
        logger.info("Tearing down test environment...");
        DriverFactory.quitDriver();
        logger.info("WebDriver quit successfully");
    }

    /**
     * Runs once after all tests in the test class.
     * Use for final cleanup operations.
     */
    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        logger.info("========================================");
        logger.info("Finished Test Class: {}", this.getClass().getSimpleName());
        logger.info("========================================");
    }

    /**
     * Helper method to get the current WebDriver instance.
     * Useful in test classes for accessing the driver.
     *
     * @return WebDriver instance
     */
    protected WebDriver getDriver() {
        return driver;
    }

    /**
     * Helper method to navigate to a URL with Allure step logging.
     *
     * @param url URL to navigate to
     */
    @Step("Navigate to URL: {url}")
    protected void navigateToUrl(String url) {
        logger.info("Navigating to: {}", url);
        driver.get(url);
    }

    /**
     * Helper method to navigate to base URL.
     * Useful when you need to reset to home page during test.
     */
    @Step("Navigate to base URL")
    protected void navigateToBaseUrl() {
        String baseUrl = PropertyReader.getProperty("base.url");
        logger.info("Navigating to base URL: {}", baseUrl);
        driver.get(baseUrl);
    }
}
/*

public class Base {
    protected WebDriver driver;

    @Step("Setting up the WebDriver and navigating to base URL")
    @BeforeMethod
    public void setup() {
        driver = DriverFactory.initializeDriver();
        driver.get(PropertyReader.getProperty("base.url"));
    }

    @Step("Tearing down the WebDriver")
    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        DriverFactory.quitDriver();
    }
}
*/