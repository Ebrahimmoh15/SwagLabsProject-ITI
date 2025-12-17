package utils.report;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.*;
import java.util.Comparator;

/**
 * Utility class for Allure report operations.
 * Handles report generation, opening, and result cleanup.
 */
public class AllureUtlis {

    private static final Logger logger = LogManager.getLogger(AllureUtlis.class);

    private static final String ALLURE_RESULTS_DIR = "test-output/allure-results";
    private static final String ALLURE_REPORT_DIR = "test-output/allure-report";

    /**
     * Cleans all files from the Allure results directory.
     * Should be called before test execution starts.
     */
    public static void AllureCleanResult() {
        logger.info("Cleaning Allure results directory...");

        try {
            Path resultsPath = Paths.get(ALLURE_RESULTS_DIR);

            if (Files.exists(resultsPath)) {
                Files.walk(resultsPath)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .filter(file -> !file.getPath().equals(resultsPath.toString()))
                        .forEach(File::delete);

                logger.info("Allure results directory cleaned successfully");
            } else {
                logger.info("Allure results directory does not exist yet");
            }
        } catch (IOException e) {
            logger.error("Failed to clean Allure results: {}", e.getMessage(), e);
        }
    }

    /**
     * Generates Allure report using Allure CLI command.
     * Requires Allure to be installed on the system.
     *
     * @return true if report generated successfully, false otherwise
     */
    public static boolean generateReport() {
        logger.info("═══════════════════════════════════════════════════");
        logger.info("           GENERATING ALLURE REPORT                ");
        logger.info("═══════════════════════════════════════════════════");

        try {
            // Check if results directory exists
            if (!Files.exists(Paths.get(ALLURE_RESULTS_DIR))) {
                logger.error("Results directory does not exist: {}", ALLURE_RESULTS_DIR);
                return false;
            }

            // Check if Allure CLI is installed
            if (!isAllureInstalled()) {
                logger.error("Allure CLI is not installed!");
                logger.info("Install Allure CLI:");
                logger.info("  - npm install -g allure-commandline");
                logger.info("  - Or: brew install allure (Mac)");
                logger.info("  - Or: scoop install allure (Windows)");
                return false;
            }

            // Execute Allure generate command
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "allure", "generate", ALLURE_RESULTS_DIR,
                    "-o", ALLURE_REPORT_DIR, "--clean"
            );

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Log process output
            logProcessOutput(process);

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                logger.info("✓ Allure report generated successfully!");
                logger.info("Report location: {}", new File(ALLURE_REPORT_DIR).getAbsolutePath());
                return true;
            } else {
                logger.error("✗ Failed to generate Allure report. Exit code: {}", exitCode);
                return false;
            }

        } catch (IOException | InterruptedException e) {
            logger.error("Error generating Allure report: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * Opens the generated Allure report in the default browser.
     *
     * @return true if report opened successfully, false otherwise
     */
    public static boolean openReport() {
        logger.info("═══════════════════════════════════════════════════");
        logger.info("            OPENING ALLURE REPORT                  ");
        logger.info("═══════════════════════════════════════════════════");

        try {
            // Check if report directory exists
            if (!Files.exists(Paths.get(ALLURE_REPORT_DIR))) {
                logger.error("Report directory does not exist: {}", ALLURE_REPORT_DIR);
                logger.info("Generate report first using generateReport()");
                return false;
            }

            // Check if Allure CLI is installed
            if (!isAllureInstalled()) {
                logger.error("Allure CLI is not installed!");
                return false;
            }

            // Execute Allure open command
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "allure", "open", ALLURE_REPORT_DIR
            );

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Log process output in background
            new Thread(() -> logProcessOutput(process)).start();

            logger.info("✓ Allure report server started!");
            logger.info("Opening report in browser...");
            logger.info("Press Ctrl+C to stop the server");

            return true;

        } catch (IOException e) {
            logger.error("Error opening Allure report: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * Generates and opens Allure report in one operation.
     * This is a convenience method combining generate and open.
     *
     * @return true if successful, false otherwise
     */
    public static boolean generateAndOpenReport() {
        logger.info("═══════════════════════════════════════════════════");
        logger.info("      GENERATING AND OPENING ALLURE REPORT         ");
        logger.info("═══════════════════════════════════════════════════");

        try {
            // Check if results directory exists
            if (!Files.exists(Paths.get(ALLURE_RESULTS_DIR))) {
                logger.error("Results directory does not exist: {}", ALLURE_RESULTS_DIR);
                return false;
            }

            // Check if Allure CLI is installed
            if (!isAllureInstalled()) {
                logger.error("Allure CLI is not installed!");
                logger.info("Install Allure CLI:");
                logger.info("  - npm install -g allure-commandline");
                logger.info("  - Or: brew install allure (Mac)");
                logger.info("  - Or: scoop install allure (Windows)");
                return false;
            }

            // Execute Allure serve command (generates and opens in one step)
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "allure", "serve", ALLURE_RESULTS_DIR
            );

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Log process output in background
            new Thread(() -> logProcessOutput(process)).start();

            logger.info("✓ Allure report generated and opened in browser!");
            logger.info("Press Ctrl+C to stop the server");

            return true;

        } catch (IOException e) {
            logger.error("Error generating and opening Allure report: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * Generates Allure report using Maven command.
     * Alternative method when Allure CLI is not available.
     *
     * @return true if successful, false otherwise
     */
    public static boolean generateReportViaMaven() {
        logger.info("═══════════════════════════════════════════════════");
        logger.info("      GENERATING ALLURE REPORT VIA MAVEN           ");
        logger.info("═══════════════════════════════════════════════════");

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "mvn", "allure:report"
            );

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            logProcessOutput(process);

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                logger.info("✓ Allure report generated via Maven successfully!");
                return true;
            } else {
                logger.error("✗ Failed to generate report via Maven. Exit code: {}", exitCode);
                return false;
            }

        } catch (IOException | InterruptedException e) {
            logger.error("Error generating report via Maven: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * Serves Allure report using Maven command.
     * Alternative method when Allure CLI is not available.
     *
     * @return true if successful, false otherwise
     */
    public static boolean serveReportViaMaven() {
        logger.info("═══════════════════════════════════════════════════");
        logger.info("       SERVING ALLURE REPORT VIA MAVEN             ");
        logger.info("═══════════════════════════════════════════════════");

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "mvn", "allure:serve"
            );

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Log process output in background
            new Thread(() -> logProcessOutput(process)).start();

            logger.info("✓ Allure report server started via Maven!");
            logger.info("Press Ctrl+C to stop the server");

            return true;

        } catch (IOException e) {
            logger.error("Error serving report via Maven: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * Checks if Allure CLI is installed on the system.
     *
     * @return true if Allure is installed, false otherwise
     */
    public static boolean isAllureInstalled() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("allure", "--version");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            boolean isInstalled = exitCode == 0;

            if (isInstalled) {
                // Get version info
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()))) {
                    String version = reader.readLine();
                    logger.info("Allure CLI is installed: {}", version);
                }
            }

            return isInstalled;

        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    /**
     * Gets the absolute path to the report index.html file.
     *
     * @return Path to index.html or null if not found
     */
    public static String getReportPath() {
        Path indexPath = Paths.get(ALLURE_REPORT_DIR, "index.html");

        if (Files.exists(indexPath)) {
            String absolutePath = indexPath.toAbsolutePath().toString();
            logger.info("Report found at: {}", absolutePath);
            return absolutePath;
        } else {
            logger.warn("Report not found at: {}", indexPath.toAbsolutePath());
            return null;
        }
    }

    /**
     * Logs process output for debugging.
     *
     * @param process Process to log output from
     */
    private static void logProcessOutput(Process process) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info("[Allure] {}", line);
            }
        } catch (IOException e) {
            logger.error("Error reading process output: {}", e.getMessage());
        }
    }

    /**
     * Opens report in default browser using file path.
     * Fallback method when Allure CLI is not available.
     */
    public static void openReportInBrowser() {
        try {
            String reportPath = getReportPath();
            if (reportPath == null) {
                logger.error("Cannot open report - file not found");
                return;
            }

            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder pb;

            if (os.contains("win")) {
                // Windows
                pb = new ProcessBuilder("cmd", "/c", "start", reportPath);
            } else if (os.contains("mac")) {
                // Mac
                pb = new ProcessBuilder("open", reportPath);
            } else {
                // Linux
                pb = new ProcessBuilder("xdg-open", reportPath);
            }

            pb.start();
            logger.info("✓ Report opened in default browser");

        } catch (IOException e) {
            logger.error("Failed to open report in browser: {}", e.getMessage(), e);
        }
    }
}


/*
public class AllureUtlis {

   /* public static  void AllureCleanResult ()
    {
        FileUtils.deleteQuietly( new File("test-output/allure-results"));
    }

    // Allure method generate report automatically

}
*/