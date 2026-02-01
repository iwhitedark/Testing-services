package pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page Object for English Wikipedia main page (en.wikipedia.org).
 */
public class WikipediaEnglishHomePage extends BasePage {

    // Search elements
    @FindBy(name = "search")
    private WebElement searchInput;

    @FindBy(css = "button.cdx-button")
    private WebElement searchButton;

    // Main page elements
    @FindBy(id = "mp-topbanner")
    private WebElement topBanner;

    @FindBy(css = ".mw-logo")
    private WebElement logo;

    // Navigation elements
    @FindBy(id = "n-mainpage-description")
    private WebElement mainPageLink;

    @FindBy(id = "n-contents")
    private WebElement contentsLink;

    @FindBy(id = "n-currentevents")
    private WebElement currentEventsLink;

    @FindBy(id = "n-randompage")
    private WebElement randomArticleLink;

    // Featured article section
    @FindBy(id = "mp-tfa")
    private WebElement featuredArticleSection;

    // Did you know section
    @FindBy(id = "mp-dyk")
    private WebElement didYouKnowSection;

    // In the news section
    @FindBy(id = "mp-itn")
    private WebElement inTheNewsSection;

    // Locators
    private final By searchResultsLocator = By.cssSelector(".cdx-menu-item");

    public WikipediaEnglishHomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Open English Wikipedia main page.
     * @param url Wikipedia URL
     * @return this page object
     */
    public WikipediaEnglishHomePage open(String url) {
        navigateTo(url);
        waitForPageLoad();
        return this;
    }

    /**
     * Wait for page to load.
     */
    private void waitForPageLoad() {
        waitForTitleContains("Wikipedia");
    }

    /**
     * Check if main page is loaded.
     * @return true if page is loaded
     */
    public boolean isPageLoaded() {
        return isElementDisplayed(searchInput);
    }

    /**
     * Enter search query.
     * @param query search query
     * @return this page object
     */
    public WikipediaEnglishHomePage enterSearchQuery(String query) {
        enterText(searchInput, query);
        return this;
    }

    /**
     * Click search button.
     * @return WikipediaSearchResultsPage or WikipediaArticlePage
     */
    public WikipediaSearchResultsPage clickSearchButton() {
        click(searchButton);
        return new WikipediaSearchResultsPage(driver);
    }

    /**
     * Perform search.
     * @param query search query
     * @return WikipediaSearchResultsPage
     */
    public WikipediaSearchResultsPage search(String query) {
        enterSearchQuery(query);
        return clickSearchButton();
    }

    /**
     * Click on random article link.
     * @return WikipediaArticlePage
     */
    public WikipediaArticlePage clickRandomArticle() {
        click(randomArticleLink);
        return new WikipediaArticlePage(driver);
    }

    /**
     * Check if logo is displayed.
     * @return true if displayed
     */
    public boolean isLogoDisplayed() {
        return isElementDisplayed(logo);
    }

    /**
     * Check if search input is displayed.
     * @return true if displayed
     */
    public boolean isSearchInputDisplayed() {
        return isElementDisplayed(searchInput);
    }

    /**
     * Check if featured article section is displayed.
     * @return true if displayed
     */
    public boolean isFeaturedArticleSectionDisplayed() {
        return isElementDisplayed(featuredArticleSection);
    }

    /**
     * Check if "Did you know" section is displayed.
     * @return true if displayed
     */
    public boolean isDidYouKnowSectionDisplayed() {
        return isElementDisplayed(didYouKnowSection);
    }

    /**
     * Check if "In the news" section is displayed.
     * @return true if displayed
     */
    public boolean isInTheNewsSectionDisplayed() {
        return isElementDisplayed(inTheNewsSection);
    }

    /**
     * Get page title.
     * @return page title
     */
    public String getTitle() {
        return getPageTitle();
    }

    /**
     * Click on Main page link in navigation.
     * @return this page object
     */
    public WikipediaEnglishHomePage clickMainPageLink() {
        click(mainPageLink);
        return this;
    }

    /**
     * Click on Contents link in navigation.
     */
    public void clickContentsLink() {
        click(contentsLink);
    }

    /**
     * Click on Current events link in navigation.
     */
    public void clickCurrentEventsLink() {
        click(currentEventsLink);
    }

    /**
     * Get search suggestions (autocomplete results).
     * @return list of suggestion elements
     */
    public List<WebElement> getSearchSuggestions() {
        try {
            Thread.sleep(500);
            return driver.findElements(searchResultsLocator);
        } catch (Exception e) {
            return List.of();
        }
    }
}
