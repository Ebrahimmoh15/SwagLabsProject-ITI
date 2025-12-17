package utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PropertyReader {

    private static final Properties properties = new Properties();
    private static String environment;

    static {
        try {
            // 1️⃣ Determine environment (default = dev)
            environment = System.getProperty("env", "dev").toLowerCase();
            System.out.println("🌍 Active environment: " + environment);

            // 2️⃣ Load all properties files (common and environment)
            Collection<File> files = FileUtils.listFiles(new File("src/test/resources/"), new String[]{"properties"}, true);
            files.addAll(FileUtils.listFiles(new File("src/main/resources/"), new String[]{"properties"}, true));

            for (File file : files) {
                // Load only matching environment or global config files
                if (file.getName().equalsIgnoreCase("config-" + environment + ".properties") ||
                        file.getName().equalsIgnoreCase("global.properties")) {
                    try (InputStream in = FileUtils.openInputStream(file)) {
                        properties.load(in);
                        System.out.println("✅ Loaded: " + file.getName());
                    } catch (IOException e) {
                        System.out.println("⚠️ Failed to load: " + file.getName() + " - " + e.getMessage());
                    }
                }
            }

            // 3️⃣ Resolve placeholders like ${base.url}
            resolvePlaceholders();

        } catch (Exception e) {
            System.out.println("❌ Failed to load properties: " + e.getMessage());
        }
    }
    public static Properties loadProperties() {
        try {
            Properties properties = new Properties();
            Collection<File> propertiesFiles;
            propertiesFiles = FileUtils.listFiles(new File("src/main/resources"), new String[]{"properties"}, true); //get all files with extension properties
            propertiesFiles.forEach(file -> {
                try {
                    properties.load(new FileInputStream(file));
                } catch (Exception e) {
                    LogsManager.error("Error loading properties file:", file.getName(), e.getMessage());
                }
                properties.putAll(System.getProperties());
                System.getProperties().putAll(properties);
            });
            LogsManager.info("Properties file loaded successfully");
            return properties;
        } catch (Exception e) {
            LogsManager.error("Error loading properties file", e.getMessage());
            return null;
        }
    }

    private static void resolvePlaceholders() {
        Pattern pattern = Pattern.compile("\\$\\{([^}]+)}");

        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            Matcher matcher = pattern.matcher(value);
            StringBuffer sb = new StringBuffer();

            while (matcher.find()) {
                String referencedKey = matcher.group(1);
                String replacement = properties.getProperty(referencedKey, "");
                matcher.appendReplacement(sb, replacement);
            }
            matcher.appendTail(sb);
            properties.setProperty(key, sb.toString());
        }
    }


    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

 /*public static String getPropertySystem(String key) {
        try {
            return System.getProperty(key);
        } catch (Exception e) {
            LogsManager.error("Error getting property for key:", key, e.getMessage());
            return "";
        }
    }*/
    public static String getEnvironment() {
        return PropertyReader.environment;
    }
}
