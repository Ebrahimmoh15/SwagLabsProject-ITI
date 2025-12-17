package listeners;

import Media.ScreenRecorder;
import Media.ScreenshotUtils;
import Drivers.DriverFactory;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;
import org.openqa.selenium.WebDriver;
import utils.report.AllureUtlis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * TestNG Listener for automatic screenshot capture, video recording, and Allure report generation.
 * Implements:
 * - ITestListener: For test-level events
 * - IInvokedMethodListener: For method-level events (recording control)
 * - ISuiteListener: For suite-level events (report generation)
 */
public class TestListener implements ITestListener, IInvokedMethodListener, ISuiteListener {

    private static final Logger logger = LogManager.getLogger(TestListener.class);

    // Configuration: Set to true to auto-generate and open report after tests
    private static final boolean AUTO_GENERATE_REPORT =
            Boolean.parseBoolean(System.getProperty("autoGenerateReport", "true"));
    private static final boolean AUTO_OPEN_REPORT =
            Boolean.parseBoolean(System.getProperty("autoOpenReport", "true"));

    // Ensure Allure results dir is set before Allure lifecycle/attachments are used
    static {
        System.setProperty("allure.results.directory", "test-output/allure-results");
        File dir = new File("test-output/allure-results");
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            logger.info("Created allure results directory: {} -> {}", dir.getAbsolutePath(), created);
        } else {
            logger.info("Allure results directory exists: {}", dir.getAbsolutePath());
        }
        AllureUtlis.AllureCleanResult();
    }

    // =====================================
    // SUITE LEVEL LISTENERS
    // =====================================

    /**
     * Called when the entire test suite starts.
     */
    @Override
    public void onStart(ISuite suite) {
        logger.info("╔══════════════════════════════════════════════════════╗");
        logger.info("║       TEST SUITE EXECUTION STARTED                   ║");
        logger.info("╚══════════════════════════════════════════════════════╝");
        logger.info("Suite Name: {}", suite.getName());
        logger.info("Auto Generate Report: {}", AUTO_GENERATE_REPORT);
        logger.info("Auto Open Report: {}", AUTO_OPEN_REPORT);
    }

    /**
     * Called when the entire test suite finishes.
     * This is where we generate and open the Allure report.
     */
    @Override
    public void onFinish(ISuite suite) {
        logger.info("╔══════════════════════════════════════════════════════╗");
        logger.info("║       TEST SUITE EXECUTION COMPLETED                 ║");
        logger.info("╚══════════════════════════════════════════════════════╝");
        logger.info("Suite Name: {}", suite.getName());

        // Log suite results summary
        logSuiteSummary(suite);

        // Generate Allure Report if enabled
        if (AUTO_GENERATE_REPORT) {
            generateAllureReport();
        }

        // Open report in browser if enabled
        if (AUTO_OPEN_REPORT) {
            openAllureReport();
        }

        logger.info("═══════════════════════════════════════════════════════");
        logger.info("Test execution completed. Thank you!");
        logger.info("═══════════════════════════════════════════════════════");
    }

    // =====================================
    // TEST CONTEXT LISTENERS
    // =====================================

    @Override
    public void onStart(ITestContext context) {
        logger.info("═══════════════════════════════════════════════════════");
        logger.info("TestNG context started: {}", context.getName());
        logger.info("═══════════════════════════════════════════════════════");
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("═══════════════════════════════════════════════════════");
        logger.info("TestNG context finished: {}", context.getName());
        logger.info("Passed: {} | Failed: {} | Skipped: {}",
                context.getPassedTests().size(),
                context.getFailedTests().size(),
                context.getSkippedTests().size());
        logger.info("═══════════════════════════════════════════════════════");
    }

    // =====================================
    // INVOKED METHOD LISTENERS
    // =====================================

    /**
     * Called before each test method invocation.
     * Starts screen recording.
     */
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            try {
                String name = testResult.getMethod().getMethodName();
                ScreenRecorder.startRecording(name);
                logger.info("▶ Started recording for: {}", name);
            } catch (Exception e) {
                logger.error("Failed to start recording: {}", e.getMessage(), e);
            }
        }
    }

    /**
     * Called after each test method invocation.
     * Captures screenshot and stops recording.
     */
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (!method.isTestMethod()) return;

        String testName = testResult.getMethod().getMethodName();

        try {
            WebDriver driver = DriverFactory.getDriver();

            // Always capture screenshot for every outcome
            try {
                if (driver != null && ScreenshotUtils.canTakeScreenshot(driver)) {
                    String prefix = testResult.isSuccess() ? "SUCCESS" :
                            (testResult.getStatus() == ITestResult.SKIP ? "SKIPPED" : "FAILURE");
                    String screenshotName = ScreenshotUtils.generateScreenshotName(prefix, testName);
                    ScreenshotUtils.captureScreenshotToAllure(driver, screenshotName);
                    logger.info("✓ Screenshot captured: {}", screenshotName);
                } else {
                    logger.warn("⚠ No WebDriver available to take screenshot for: {}", testName);
                }
            } catch (Exception e) {
                logger.error("✗ Error capturing screenshot: {}", e.getMessage(), e);
            }

            // Stop recording and attach video
            try {
                File video = ScreenRecorder.stopRecording();
                if (video != null && video.exists()) {
                    try (FileInputStream fis = new FileInputStream(video)) {
                        Allure.addAttachment("Video - " + testName,
                                "video/avi", fis, ".avi");
                        logger.info("✓ Video attached: {}", video.getAbsolutePath());
                    }
                } else {
                    logger.warn("⚠ No video file created for: {}", testName);
                }
            } catch (Exception e) {
                logger.error("✗ Failed to stop/attach video: {}", e.getMessage(), e);
            }

        } catch (Exception e) {
            logger.error("✗ Error in afterInvocation listener: {}", e.getMessage(), e);
        }
    }

    // =====================================
    // TEST LISTENERS (No-op implementations)
    // =====================================

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("▶ Test Started: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("✓ Test Passed: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("✗ Test Failed: {}", result.getMethod().getMethodName());
        if (result.getThrowable() != null) {
            logger.error("Failure reason: {}", result.getThrowable().getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("⊘ Test Skipped: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.warn("⚠ Test Failed Within Success Percentage: {}", result.getMethod().getMethodName());
    }

    // =====================================
    // HELPER METHODS
    // =====================================

    /**
     * Generates the Allure report after test execution.
     */
    private void generateAllureReport() {
        logger.info("");
        logger.info("╔══════════════════════════════════════════════════════╗");
        logger.info("║         GENERATING ALLURE REPORT                     ║");
        logger.info("╚══════════════════════════════════════════════════════╝");

        try {
            // Try Allure CLI first
            if (AllureUtlis.isAllureInstalled()) {
                logger.info("Using Allure CLI to generate report...");
                boolean success = AllureUtlis.generateReport();

                if (success) {
                    logger.info("✓ Report generated successfully!");
                    String reportPath = AllureUtlis.getReportPath();
                    if (reportPath != null) {
                        logger.info("Report location: {}", reportPath);
                    }
                } else {
                    logger.error("✗ Failed to generate report via Allure CLI");
                }
            } else {
                // Fallback to Maven
                logger.warn("Allure CLI not found. Attempting to use Maven...");
                logger.info("To install Allure CLI:");
                logger.info("  • npm install -g allure-commandline");
                logger.info("  • brew install allure (Mac)");
                logger.info("  • scoop install allure (Windows)");

                boolean success = AllureUtlis.generateReportViaMaven();
                if (!success) {
                    logger.error("✗ Failed to generate report via Maven");
                    logger.info("Manual generation: mvn allure:report");
                }
            }
        } catch (Exception e) {
            logger.error("✗ Error during report generation: {}", e.getMessage(), e);
        }
    }

    /**
     * Opens the Allure report in browser.
     */
    private void openAllureReport() {
        logger.info("");
        logger.info("╔══════════════════════════════════════════════════════╗");
        logger.info("║          OPENING ALLURE REPORT                       ║");
        logger.info("╚══════════════════════════════════════════════════════╝");

        try {
            if (AllureUtlis.isAllureInstalled()) {
                logger.info("Opening report using Allure CLI...");
                boolean success = AllureUtlis.openReport();

                if (success) {
                    logger.info("✓ Report opened in browser!");
                    logger.info("Server is running. Press Ctrl+C to stop.");
                } else {
                    logger.error("✗ Failed to open report");
                    logger.info("Manual open: allure open test-output/allure-report");
                }
            } else {
                // Fallback to opening HTML file directly
                logger.warn("Allure CLI not found. Opening report file directly...");
                AllureUtlis.openReportInBrowser();
            }
        } catch (Exception e) {
            logger.error("✗ Error opening report: {}", e.getMessage(), e);
            logger.info("You can manually open: test-output/allure-report/index.html");
        }
    }

    /**
     * Logs summary of suite execution results.
     */
    private void logSuiteSummary(ISuite suite) {
        int totalTests = 0;
        int passed = 0;
        int failed = 0;
        int skipped = 0;

        for (ISuiteResult result : suite.getResults().values()) {
            ITestContext context = result.getTestContext();
            totalTests += context.getAllTestMethods().length;
            passed += context.getPassedTests().size();
            failed += context.getFailedTests().size();
            skipped += context.getSkippedTests().size();
        }

        logger.info("═══════════════════════════════════════════════════════");
        logger.info("              TEST EXECUTION SUMMARY                   ");
        logger.info("═══════════════════════════════════════════════════════");
        logger.info("Total Tests:  {}", totalTests);
        logger.info("✓ Passed:     {}", passed);
        logger.info("✗ Failed:     {}", failed);
        logger.info("⊘ Skipped:    {}", skipped);

        if (totalTests > 0) {
            double successRate = (passed * 100.0) / totalTests;
            logger.info("Success Rate: {:.2f}%", successRate);
        }

        logger.info("═══════════════════════════════════════════════════════");
    }
}