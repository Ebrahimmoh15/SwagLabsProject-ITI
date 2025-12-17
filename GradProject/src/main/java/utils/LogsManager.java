package utils;

import io.qameta.allure.AllureConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogsManager {

    public static final String LOGS_PATH = AllureConstants.TEST_RESULT_FILE_GLOB + "/test-output/Logs/";

    private static Logger logger ()
    {
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[3].getClassName());
    }

    public static void info (String message)
    {
        logger().info(message);
    }
    public static void error (String message)
    {
        logger().error(message);
    }
    public static void warn (String message)
    {
        logger().warn(message);
    }
    public static void debug (String message)
    {
        logger().debug(message);
    }
    public static void fatal (String message)
    {
        logger().fatal(message);
    }
    public static void error(String... message) {
        logger().error(String.join(" ", message));
    }
}
