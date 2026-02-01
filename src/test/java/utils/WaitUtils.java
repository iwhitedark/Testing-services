package utils;

import config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Utility class for explicit waits and common wait operations.
 */
public class WaitUtils {

    private final WebDriverWait wait;
    private final WebDriver driver;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
    }

    public WaitUtils(WebDriver driver, int timeoutSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    /**
     * Wait for element to be visible.
     * @param locator element locator
     * @return visible WebElement
     */
    public WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be visible.
     * @param element WebElement
     * @return visible WebElement
     */
    public WebElement waitForElementVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for element to be clickable.
     * @param locator element locator
     * @return clickable WebElement
     */
    public WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for element to be clickable.
     * @param element WebElement
     * @return clickable WebElement
     */
    public WebElement waitForElementClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for element to be present in DOM.
     * @param locator element locator
     * @return WebElement
     */
    public WebElement waitForElementPresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Wait for all elements to be visible.
     * @param locator element locator
     * @return list of visible WebElements
     */
    public List<WebElement> waitForAllElementsVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * Wait for element to contain specific text.
     * @param locator element locator
     * @param text expected text
     * @return true if element contains text
     */
    public boolean waitForTextPresent(By locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    /**
     * Wait for URL to contain specific text.
     * @param urlPart expected URL part
     * @return true if URL contains text
     */
    public boolean waitForUrlContains(String urlPart) {
        return wait.until(ExpectedConditions.urlContains(urlPart));
    }

    /**
     * Wait for page title to contain specific text.
     * @param titlePart expected title part
     * @return true if title contains text
     */
    public boolean waitForTitleContains(String titlePart) {
        return wait.until(ExpectedConditions.titleContains(titlePart));
    }

    /**
     * Wait for element to disappear.
     * @param locator element locator
     * @return true if element is invisible
     */
    public boolean waitForElementInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Wait for specified number of elements.
     * @param locator element locator
     * @param count expected count
     * @return list of WebElements
     */
    public List<WebElement> waitForNumberOfElements(By locator, int count) {
        return wait.until(ExpectedConditions.numberOfElementsToBe(locator, count));
    }

    /**
     * Wait for at least specified number of elements.
     * @param locator element locator
     * @param minCount minimum expected count
     * @return list of WebElements
     */
    public List<WebElement> waitForMinimumNumberOfElements(By locator, int minCount) {
        return wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, minCount - 1));
    }

    /**
     * Check if element is displayed without waiting.
     * @param locator element locator
     * @return true if element is displayed
     */
    public boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
