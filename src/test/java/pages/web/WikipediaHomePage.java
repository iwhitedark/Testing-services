package pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page Object for Wikipedia main page (www.wikipedia.org).
 */
public class WikipediaHomePage extends BasePage {

    // Search elements
    @FindBy(id = "searchInput")
    private WebElement searchInput;

    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;

    // Language links
    @FindBy(id = "js-link-box-en")
    private WebElement englishLink;

    @FindBy(id = "js-link-box-ru")
    private WebElement russianLink;

    @FindBy(id = "js-link-box-de")
    private WebElement germanLink;

    @FindBy(id = "js-link-box-fr")
    private WebElement frenchLink;

    @FindBy(id = "js-link-box-es")
    private WebElement spanishLink;

    // Logo
    @FindBy(css = ".central-textlogo-wrapper")
    private WebElement centralLogo;

    // Language selector
    @FindBy(id = "searchLanguage")
    private WebElement languageSelector;

    // Locators for dynamic elements
    private final By searchSuggestionsLocator = By.cssSelector(".suggestion-link");
    private final By languageLinksLocator = By.cssSelector(".central-featured-lang");

    public WikipediaHomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Open Wikipedia main page.
     * @param url Wikipedia URL
     * @return this page object
     */
    public WikipediaHomePage open(String url) {
        navigateTo(url);
        waitForElementVisible(centralLogo);
        return this;
    }

    /**
     * Check if main page is loaded.
     * @return true if page is loaded
     */
    public boolean isPageLoaded() {
        return isElementDisplayed(centralLogo) && isElementDisplayed(searchInput);
    }

    /**
     * Enter search query.
     * @param query search query
     * @return this page object
     */
    public WikipediaHomePage enterSearchQuery(String query) {
        enterText(searchInput, query);
        return this;
    }

    /**
     * Click search button to perform search.
     * @return WikipediaSearchResultsPage
     */
    public WikipediaSearchResultsPage clickSearch() {
        click(searchButton);
        return new WikipediaSearchResultsPage(driver);
    }

    /**
     * Perform search by entering query and clicking search button.
     * @param query search query
     * @return WikipediaSearchResultsPage
     */
    public WikipediaSearchResultsPage search(String query) {
        enterSearchQuery(query);
        return clickSearch();
    }

    /**
     * Get search suggestions after typing.
     * @return list of suggestion WebElements
     */
    public List<WebElement> getSearchSuggestions() {
        try {
            Thread.sleep(500); // Wait for suggestions to appear
            return waitForAllElementsVisible(searchSuggestionsLocator);
        } catch (Exception e) {
            return List.of();
        }
    }

    /**
     * Click on first search suggestion.
     * @return WikipediaArticlePage
     */
    public WikipediaArticlePage clickFirstSuggestion() {
        List<WebElement> suggestions = getSearchSuggestions();
        if (!suggestions.isEmpty()) {
            click(suggestions.get(0));
            return new WikipediaArticlePage(driver);
        }
        throw new RuntimeException("No search suggestions found");
    }

    /**
     * Click English language link.
     * @return WikipediaEnglishHomePage
     */
    public WikipediaEnglishHomePage clickEnglishLink() {
        click(englishLink);
        return new WikipediaEnglishHomePage(driver);
    }

    /**
     * Click Russian language link.
     */
    public void clickRussianLink() {
        click(russianLink);
    }

    /**
     * Click German language link.
     */
    public void clickGermanLink() {
        click(germanLink);
    }

    /**
     * Click French language link.
     */
    public void clickFrenchLink() {
        click(frenchLink);
    }

    /**
     * Click Spanish language link.
     */
    public void clickSpanishLink() {
        click(spanishLink);
    }

    /**
     * Check if English link is displayed.
     * @return true if displayed
     */
    public boolean isEnglishLinkDisplayed() {
        return isElementDisplayed(englishLink);
    }

    /**
     * Check if Russian link is displayed.
     * @return true if displayed
     */
    public boolean isRussianLinkDisplayed() {
        return isElementDisplayed(russianLink);
    }

    /**
     * Check if search input is displayed.
     * @return true if displayed
     */
    public boolean isSearchInputDisplayed() {
        return isElementDisplayed(searchInput);
    }

    /**
     * Check if central logo is displayed.
     * @return true if displayed
     */
    public boolean isLogoDisplayed() {
        return isElementDisplayed(centralLogo);
    }

    /**
     * Get number of main language links.
     * @return count of language links
     */
    public int getMainLanguageLinksCount() {
        List<WebElement> links = driver.findElements(languageLinksLocator);
        return links.size();
    }

    /**
     * Select search language from dropdown.
     * @param languageCode language code (e.g., "en", "ru")
     */
    public void selectSearchLanguage(String languageCode) {
        click(languageSelector);
        By languageOption = By.cssSelector("option[lang='" + languageCode + "']");
        click(languageOption);
    }

    /**
     * Get search input placeholder text.
     * @return placeholder text
     */
    public String getSearchPlaceholder() {
        return searchInput.getAttribute("placeholder");
    }
}
