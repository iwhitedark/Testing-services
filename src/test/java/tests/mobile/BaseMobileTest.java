package tests.mobile;

import config.ConfigReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Base test class for mobile tests.
 * Handles Appium driver setup and teardown.
 */
public class BaseMobileTest {

    protected AndroidDriver driver;

    @BeforeClass
    @Parameters({"deviceName", "platformVersion"})
    public void setUp(@Optional("") String deviceName, @Optional("") String platformVersion) {
        try {
            UiAutomator2Options options = createOptions(deviceName, platformVersion);
            URL appiumServerUrl = new URL(ConfigReader.getAppiumServerUrl());
            driver = new AndroidDriver(appiumServerUrl, options);
            configureDriver();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium server URL: " + e.getMessage());
        }
    }

    /**
     * Create UiAutomator2Options for driver initialization.
     * @param deviceName device name parameter
     * @param platformVersion platform version parameter
     * @return configured options
     */
    private UiAutomator2Options createOptions(String deviceName, String platformVersion) {
        UiAutomator2Options options = new UiAutomator2Options();

        // Platform settings
        options.setPlatformName(ConfigReader.getAndroidPlatformName());

        // Device settings
        String device = deviceName.isEmpty() ? ConfigReader.getAndroidDeviceName() : deviceName;
        options.setDeviceName(device);

        // Platform version
        String version = platformVersion.isEmpty() ? ConfigReader.getAndroidPlatformVersion() : platformVersion;
        options.setPlatformVersion(version);

        // Automation settings
        options.setAutomationName(ConfigReader.getAndroidAutomationName());

        // App settings - use package and activity
        options.setAppPackage(ConfigReader.getWikipediaAppPackage());
        options.setAppActivity(ConfigReader.getWikipediaAppActivity());

        // If APK path is specified, use it
        String apkPath = ConfigReader.getWikipediaApkPath();
        if (apkPath != null && !apkPath.isEmpty()) {
            options.setApp(apkPath);
        }

        // Additional capabilities for stability
        options.setNoReset(false);
        options.setFullReset(false);
        options.setAutoGrantPermissions(true);

        return options;
    }

    /**
     * Configure driver timeouts.
     */
    private void configureDriver() {
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(ConfigReader.getMobileImplicitWait())
        );
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Get the AndroidDriver instance.
     * @return AndroidDriver
     */
    protected AndroidDriver getDriver() {
        return driver;
    }

    /**
     * Restart the app.
     */
    protected void restartApp() {
        driver.terminateApp(ConfigReader.getWikipediaAppPackage());
        driver.activateApp(ConfigReader.getWikipediaAppPackage());
    }

    /**
     * Close the app without terminating the session.
     */
    protected void closeApp() {
        driver.terminateApp(ConfigReader.getWikipediaAppPackage());
    }

    /**
     * Launch the app.
     */
    protected void launchApp() {
        driver.activateApp(ConfigReader.getWikipediaAppPackage());
    }
}
