package pages.mobile;

import config.ConfigReader;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Base page class for mobile pages.
 * Contains common methods and utilities for all mobile page objects.
 */
public abstract class MobileBasePage {

    protected AndroidDriver driver;
    protected WebDriverWait wait;

    public MobileBasePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getMobileExplicitWait()));
        PageFactory.initElements(driver, this);
    }

    /**
     * Wait for element to be visible.
     * @param locator element locator
     * @return visible WebElement
     */
    protected WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be visible.
     * @param element WebElement
     * @return visible WebElement
     */
    protected WebElement waitForElementVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for element to be clickable.
     * @param locator element locator
     * @return clickable WebElement
     */
    protected WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for element to be clickable.
     * @param element WebElement
     * @return clickable WebElement
     */
    protected WebElement waitForElementClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for all elements to be present.
     * @param locator element locator
     * @return list of WebElements
     */
    protected List<WebElement> waitForAllElementsPresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    /**
     * Click element with wait.
     * @param element WebElement to click
     */
    protected void click(WebElement element) {
        waitForElementClickable(element).click();
    }

    /**
     * Click element with wait by locator.
     * @param locator element locator
     */
    protected void click(By locator) {
        waitForElementClickable(locator).click();
    }

    /**
     * Enter text into element.
     * @param element WebElement
     * @param text text to enter
     */
    protected void enterText(WebElement element, String text) {
        waitForElementVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Enter text into element by locator.
     * @param locator element locator
     * @param text text to enter
     */
    protected void enterText(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get element text.
     * @param element WebElement
     * @return element text
     */
    protected String getText(WebElement element) {
        return waitForElementVisible(element).getText();
    }

    /**
     * Get element text by locator.
     * @param locator element locator
     * @return element text
     */
    protected String getText(By locator) {
        return waitForElementVisible(locator).getText();
    }

    /**
     * Check if element is displayed.
     * @param element WebElement
     * @return true if displayed
     */
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if element is displayed by locator.
     * @param locator element locator
     * @return true if displayed
     */
    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Scroll down on the screen using UiScrollable.
     */
    protected void scrollDown() {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"));
    }

    /**
     * Scroll to element with text using UiScrollable.
     * @param text text to find
     * @return found WebElement
     */
    protected WebElement scrollToText(String text) {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                ".scrollIntoView(new UiSelector().textContains(\"" + text + "\"))"));
    }

    /**
     * Scroll to element with resource ID using UiScrollable.
     * @param resourceId resource ID
     * @return found WebElement
     */
    protected WebElement scrollToResourceId(String resourceId) {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                ".scrollIntoView(new UiSelector().resourceId(\"" + resourceId + "\"))"));
    }

    /**
     * Press back button.
     */
    protected void pressBack() {
        driver.navigate().back();
    }

    /**
     * Hide keyboard if visible.
     */
    protected void hideKeyboard() {
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
            // Keyboard not visible
        }
    }

    /**
     * Wait for element to disappear.
     * @param locator element locator
     * @return true if element is invisible
     */
    protected boolean waitForElementInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Find element by accessibility ID.
     * @param accessibilityId accessibility ID
     * @return WebElement
     */
    protected WebElement findByAccessibilityId(String accessibilityId) {
        return driver.findElement(AppiumBy.accessibilityId(accessibilityId));
    }

    /**
     * Find element by Android UI Automator.
     * @param uiAutomatorSelector UI Automator selector
     * @return WebElement
     */
    protected WebElement findByUiAutomator(String uiAutomatorSelector) {
        return driver.findElement(AppiumBy.androidUIAutomator(uiAutomatorSelector));
    }
}
