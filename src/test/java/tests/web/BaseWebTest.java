package tests.web;

import config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;

/**
 * Base test class for web tests.
 * Handles WebDriver setup and teardown.
 */
public class BaseWebTest {

    protected WebDriver driver;

    @BeforeClass
    @Parameters({"browser"})
    public void setUp(@Optional("") String browserParam) {
        String browser = browserParam.isEmpty() ? ConfigReader.getBrowser() : browserParam;
        boolean headless = ConfigReader.isHeadless();

        driver = initializeDriver(browser, headless);
        configureDriver();
    }

    /**
     * Initialize WebDriver based on browser configuration.
     * @param browser browser name
     * @param headless run in headless mode
     * @return WebDriver instance
     */
    private WebDriver initializeDriver(String browser, boolean headless) {
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                return new FirefoxDriver(firefoxOptions);

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless");
                }
                return new EdgeDriver(edgeOptions);

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless");
                }
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--window-size=1920,1080");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                return new ChromeDriver(chromeOptions);
        }
    }

    /**
     * Configure WebDriver timeouts and settings.
     */
    private void configureDriver() {
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(ConfigReader.getImplicitWait())
        );
        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(ConfigReader.getPageLoadTimeout())
        );
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Get the WebDriver instance.
     * @return WebDriver
     */
    protected WebDriver getDriver() {
        return driver;
    }
}
