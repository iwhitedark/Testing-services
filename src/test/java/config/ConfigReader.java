package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration reader utility class.
 * Loads and provides access to configuration properties from config.properties file.
 */
public class ConfigReader {

    private static Properties properties;
    private static final String CONFIG_PATH = "src/test/resources/config.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        properties = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file: " + e.getMessage());
        }
    }

    /**
     * Get property value by key.
     * @param key property key
     * @return property value or null if not found
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get property value with default.
     * @param key property key
     * @param defaultValue default value if property not found
     * @return property value or default
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get property as integer.
     * @param key property key
     * @return integer value
     */
    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }

    /**
     * Get property as integer with default.
     * @param key property key
     * @param defaultValue default value
     * @return integer value
     */
    public static int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        return value != null ? Integer.parseInt(value) : defaultValue;
    }

    /**
     * Get property as boolean.
     * @param key property key
     * @return boolean value
     */
    public static boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }

    // Web configuration getters
    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }

    public static boolean isHeadless() {
        return getBooleanProperty("headless");
    }

    public static int getImplicitWait() {
        return getIntProperty("implicit.wait", 10);
    }

    public static int getExplicitWait() {
        return getIntProperty("explicit.wait", 15);
    }

    public static int getPageLoadTimeout() {
        return getIntProperty("page.load.timeout", 30);
    }

    public static String getWebBaseUrl() {
        return getProperty("web.base.url");
    }

    public static String getWikipediaEnUrl() {
        return getProperty("web.wikipedia.en.url");
    }

    // Mobile configuration getters
    public static String getAppiumServerUrl() {
        return getProperty("appium.server.url");
    }

    public static String getAndroidPlatformName() {
        return getProperty("android.platform.name");
    }

    public static String getAndroidPlatformVersion() {
        return getProperty("android.platform.version");
    }

    public static String getAndroidDeviceName() {
        return getProperty("android.device.name");
    }

    public static String getAndroidAutomationName() {
        return getProperty("android.automation.name");
    }

    public static String getWikipediaAppPackage() {
        return getProperty("wikipedia.app.package");
    }

    public static String getWikipediaAppActivity() {
        return getProperty("wikipedia.app.activity");
    }

    public static String getWikipediaApkPath() {
        return getProperty("wikipedia.apk.path");
    }

    public static int getMobileImplicitWait() {
        return getIntProperty("mobile.implicit.wait", 10);
    }

    public static int getMobileExplicitWait() {
        return getIntProperty("mobile.explicit.wait", 20);
    }
}
