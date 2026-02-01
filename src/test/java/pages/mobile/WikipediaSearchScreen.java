package pages.mobile;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object for Wikipedia Android app search screen.
 */
public class WikipediaSearchScreen extends MobileBasePage {

    // Search input
    @FindBy(id = "org.wikipedia:id/search_src_text")
    private WebElement searchInput;

    @FindBy(id = "org.wikipedia:id/search_close_btn")
    private WebElement searchCloseButton;

    @FindBy(id = "org.wikipedia:id/search_cab_view")
    private WebElement searchCabView;

    // Search results
    @FindBy(id = "org.wikipedia:id/page_list_item_title")
    private List<WebElement> searchResultTitles;

    @FindBy(id = "org.wikipedia:id/page_list_item_description")
    private List<WebElement> searchResultDescriptions;

    @FindBy(id = "org.wikipedia:id/search_results_list")
    private WebElement searchResultsList;

    // Search result container
    @FindBy(id = "org.wikipedia:id/page_list_item_container")
    private List<WebElement> searchResultContainers;

    // No results
    @FindBy(id = "org.wikipedia:id/search_empty_view")
    private WebElement emptyView;

    // Recent searches
    @FindBy(id = "org.wikipedia:id/recent_searches_list")
    private WebElement recentSearchesList;

    // Locators
    private final By searchInputLocator = By.id("org.wikipedia:id/search_src_text");
    private final By searchResultTitleLocator = By.id("org.wikipedia:id/page_list_item_title");
    private final By searchResultsListLocator = By.id("org.wikipedia:id/search_results_list");

    public WikipediaSearchScreen(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Wait for search screen to load.
     * @return this screen
     */
    public WikipediaSearchScreen waitForSearchScreen() {
        waitForElementVisible(searchInputLocator);
        return this;
    }

    /**
     * Check if search screen is loaded.
     * @return true if loaded
     */
    public boolean isSearchScreenLoaded() {
        return isElementDisplayed(searchInputLocator);
    }

    /**
     * Enter search query.
     * @param query search query
     * @return this screen
     */
    public WikipediaSearchScreen enterSearchQuery(String query) {
        waitForElementVisible(searchInput);
        searchInput.clear();
        searchInput.sendKeys(query);
        return this;
    }

    /**
     * Clear search query.
     * @return this screen
     */
    public WikipediaSearchScreen clearSearch() {
        if (isElementDisplayed(searchCloseButton)) {
            click(searchCloseButton);
        }
        return this;
    }

    /**
     * Wait for search results to appear.
     * @return this screen
     */
    public WikipediaSearchScreen waitForSearchResults() {
        try {
            Thread.sleep(1000); // Wait for API response
            waitForElementVisible(searchResultsListLocator);
        } catch (Exception e) {
            // Results may not appear
        }
        return this;
    }

    /**
     * Check if search results are displayed.
     * @return true if results exist
     */
    public boolean hasSearchResults() {
        try {
            Thread.sleep(500);
            return !searchResultTitles.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get number of search results.
     * @return count of results
     */
    public int getSearchResultsCount() {
        return searchResultTitles.size();
    }

    /**
     * Get all search result titles.
     * @return list of titles
     */
    public List<String> getSearchResultTitlesList() {
        return searchResultTitles.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Get first search result title.
     * @return first result title
     */
    public String getFirstResultTitle() {
        if (!searchResultTitles.isEmpty()) {
            return getText(searchResultTitles.get(0));
        }
        return "";
    }

    /**
     * Click on first search result.
     * @return WikipediaArticleScreen
     */
    public WikipediaArticleScreen clickFirstResult() {
        if (!searchResultTitles.isEmpty()) {
            click(searchResultTitles.get(0));
            return new WikipediaArticleScreen(driver);
        }
        throw new RuntimeException("No search results found");
    }

    /**
     * Click on search result by index.
     * @param index result index (0-based)
     * @return WikipediaArticleScreen
     */
    public WikipediaArticleScreen clickResultByIndex(int index) {
        if (index < searchResultTitles.size()) {
            click(searchResultTitles.get(index));
            return new WikipediaArticleScreen(driver);
        }
        throw new IndexOutOfBoundsException("Result index out of bounds: " + index);
    }

    /**
     * Click on search result containing specific text.
     * @param text text to match
     * @return WikipediaArticleScreen
     */
    public WikipediaArticleScreen clickResultContaining(String text) {
        for (WebElement title : searchResultTitles) {
            if (title.getText().toLowerCase().contains(text.toLowerCase())) {
                click(title);
                return new WikipediaArticleScreen(driver);
            }
        }
        throw new RuntimeException("No result containing '" + text + "' found");
    }

    /**
     * Check if empty view is displayed (no results).
     * @return true if no results
     */
    public boolean isEmptyViewDisplayed() {
        return isElementDisplayed(emptyView);
    }

    /**
     * Check if recent searches list is displayed.
     * @return true if displayed
     */
    public boolean isRecentSearchesDisplayed() {
        return isElementDisplayed(recentSearchesList);
    }

    /**
     * Perform search and wait for results.
     * @param query search query
     * @return this screen
     */
    public WikipediaSearchScreen search(String query) {
        enterSearchQuery(query);
        waitForSearchResults();
        return this;
    }

    /**
     * Check if any result contains specific text.
     * @param text text to check
     * @return true if found
     */
    public boolean anyResultContains(String text) {
        return getSearchResultTitlesList().stream()
                .anyMatch(title -> title.toLowerCase().contains(text.toLowerCase()));
    }

    /**
     * Get search input text.
     * @return current search query
     */
    public String getSearchQuery() {
        return searchInput.getText();
    }

    /**
     * Go back from search.
     * @return WikipediaMainScreen
     */
    public WikipediaMainScreen goBack() {
        pressBack();
        return new WikipediaMainScreen(driver);
    }

    /**
     * Hide keyboard.
     * @return this screen
     */
    public WikipediaSearchScreen dismissKeyboard() {
        hideKeyboard();
        return this;
    }
}
