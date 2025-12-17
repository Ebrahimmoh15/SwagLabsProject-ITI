package Media;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for capturing and attaching screenshots to Allure reports.
 * Provides multiple methods for different screenshot capture scenarios.
 */
public class ScreenshotUtils {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtils.class);

    /**
     * Captures screenshot and attaches to Allure report using @Attachment annotation.
     * This method is ideal for simple scenarios and automatically attaches to the report.
     *
     * @param driver WebDriver instance
     * @return byte array of the screenshot
     */
    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] takeScreenshot(WebDriver driver) {
        if (driver == null) {
            logger.error("WebDriver is null. Cannot take screenshot.");
            return new byte[0];
        }

        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            logger.info("Screenshot captured successfully");
            return screenshot;
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: " + e.getMessage(), e);
            return new byte[0];
        }
    }

    /**
     * Captures screenshot with a custom name and attaches to Allure report.
     * Useful when you want descriptive screenshot names in the report.
     *
     * @param driver WebDriver instance
     * @param screenshotName Custom name for the screenshot
     * @return byte array of the screenshot
     */
    @Attachment(value = "{screenshotName}", type = "image/png")
    public static byte[] takeScreenshotWithName(WebDriver driver, String screenshotName) {
        if (driver == null) {
            logger.error("WebDriver is null. Cannot take screenshot.");
            return new byte[0];
        }

        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            logger.info("Screenshot captured: " + screenshotName);
            return screenshot;
        } catch (Exception e) {
            logger.error("Failed to capture screenshot '{}': {}", screenshotName, e.getMessage(), e);
            return new byte[0];
        }
    }

    /**
     * Captures screenshot and attaches using Allure lifecycle API.
     * This approach provides more control and works well in complex scenarios.
     * Recommended for listener implementations.
     *
     * @param driver WebDriver instance
     * @param screenshotName Custom name for the screenshot
     */
    public static void captureScreenshotToAllure(WebDriver driver, String screenshotName) {
        if (driver == null) {
            logger.error("WebDriver is null. Cannot take screenshot.");
            return;
        }

        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(screenshotName, "image/png",
                    new ByteArrayInputStream(screenshot), ".png");
            logger.info("Screenshot attached to Allure: " + screenshotName);
        } catch (Exception e) {
            logger.error("Failed to capture and attach screenshot '{}': {}",
                    screenshotName, e.getMessage(), e);
        }
    }

    /**
     * Generates a timestamped screenshot name for uniqueness.
     *
     * @param prefix Prefix for the screenshot name (e.g., "Failure", "Success")
     * @param testName Name of the test method
     * @return Formatted screenshot name with timestamp
     */
    public static String generateScreenshotName(String prefix, String testName) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        return String.format("%s - %s - %s", prefix, testName, timestamp);
    }

    /**
     * Validates if the driver is capable of taking screenshots.
     *
     * @param driver WebDriver instance to validate
     * @return true if driver can take screenshots, false otherwise
     */
    public static boolean canTakeScreenshot(WebDriver driver) {
        return driver != null && driver instanceof TakesScreenshot;
    }
}