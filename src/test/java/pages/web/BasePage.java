package pages.web;

import config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Base page class for web pages.
 * Contains common methods and utilities for all web page objects.
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }

    /**
     * Wait for element to be visible and return it.
     * @param locator element locator
     * @return visible WebElement
     */
    protected WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be visible and return it.
     * @param element WebElement
     * @return visible WebElement
     */
    protected WebElement waitForElementVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for element to be clickable and return it.
     * @param locator element locator
     * @return clickable WebElement
     */
    protected WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for element to be clickable and return it.
     * @param element WebElement
     * @return clickable WebElement
     */
    protected WebElement waitForElementClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for all elements to be visible.
     * @param locator element locator
     * @return list of visible WebElements
     */
    protected List<WebElement> waitForAllElementsVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * Wait for URL to contain specific text.
     * @param urlPart expected URL part
     * @return true if URL contains text
     */
    protected boolean waitForUrlContains(String urlPart) {
        return wait.until(ExpectedConditions.urlContains(urlPart));
    }

    /**
     * Wait for page title to contain specific text.
     * @param titlePart expected title part
     * @return true if title contains text
     */
    protected boolean waitForTitleContains(String titlePart) {
        return wait.until(ExpectedConditions.titleContains(titlePart));
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
     * Enter text into element with clear.
     * @param element WebElement
     * @param text text to enter
     */
    protected void enterText(WebElement element, String text) {
        waitForElementVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Enter text into element by locator with clear.
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
     * Scroll to element using JavaScript.
     * @param element WebElement to scroll to
     */
    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Scroll down page by pixels.
     * @param pixels number of pixels to scroll
     */
    protected void scrollDown(int pixels) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, " + pixels + ");");
    }

    /**
     * Get current page URL.
     * @return current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Get current page title.
     * @return page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Navigate to URL.
     * @param url URL to navigate to
     */
    public void navigateTo(String url) {
        driver.get(url);
    }
}
