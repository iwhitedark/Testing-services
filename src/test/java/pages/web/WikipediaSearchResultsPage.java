package pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object for Wikipedia search results page.
 */
public class WikipediaSearchResultsPage extends BasePage {

    // Search results elements
    @FindBy(css = ".mw-search-result-heading a")
    private List<WebElement> searchResultLinks;

    @FindBy(css = ".mw-search-result")
    private List<WebElement> searchResults;

    @FindBy(css = ".searchresults")
    private WebElement searchResultsContainer;

    // Search input on results page
    @FindBy(name = "search")
    private WebElement searchInput;

    @FindBy(css = "button.cdx-button")
    private WebElement searchButton;

    // Pagination
    @FindBy(css = ".mw-nextlink")
    private WebElement nextPageLink;

    @FindBy(css = ".mw-prevlink")
    private WebElement prevPageLink;

    // No results message
    @FindBy(css = ".mw-search-nonefound")
    private WebElement noResultsMessage;

    // Locators
    private final By searchResultHeadingLocator = By.cssSelector(".mw-search-result-heading");
    private final By searchResultTextLocator = By.cssSelector(".searchresult");

    public WikipediaSearchResultsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Check if search results page is loaded.
     * @return true if page is loaded
     */
    public boolean isPageLoaded() {
        return waitForUrlContains("search") || waitForUrlContains("wiki/");
    }

    /**
     * Check if results are displayed.
     * @return true if results exist
     */
    public boolean hasResults() {
        return !searchResults.isEmpty();
    }

    /**
     * Get number of search results on current page.
     * @return count of results
     */
    public int getResultsCount() {
        return searchResults.size();
    }

    /**
     * Get all search result titles.
     * @return list of result titles
     */
    public List<String> getSearchResultTitles() {
        return searchResultLinks.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Click on first search result.
     * @return WikipediaArticlePage
     */
    public WikipediaArticlePage clickFirstResult() {
        if (!searchResultLinks.isEmpty()) {
            click(searchResultLinks.get(0));
            return new WikipediaArticlePage(driver);
        }
        throw new RuntimeException("No search results found");
    }

    /**
     * Click on search result by index.
     * @param index result index (0-based)
     * @return WikipediaArticlePage
     */
    public WikipediaArticlePage clickResultByIndex(int index) {
        if (index < searchResultLinks.size()) {
            click(searchResultLinks.get(index));
            return new WikipediaArticlePage(driver);
        }
        throw new IndexOutOfBoundsException("Result index out of bounds: " + index);
    }

    /**
     * Click on search result containing specific text.
     * @param text text to search in title
     * @return WikipediaArticlePage
     */
    public WikipediaArticlePage clickResultContaining(String text) {
        for (WebElement link : searchResultLinks) {
            if (link.getText().toLowerCase().contains(text.toLowerCase())) {
                click(link);
                return new WikipediaArticlePage(driver);
            }
        }
        throw new RuntimeException("No result containing '" + text + "' found");
    }

    /**
     * Perform new search from results page.
     * @param query new search query
     * @return WikipediaSearchResultsPage
     */
    public WikipediaSearchResultsPage search(String query) {
        enterText(searchInput, query);
        click(searchButton);
        return new WikipediaSearchResultsPage(driver);
    }

    /**
     * Check if next page link is available.
     * @return true if next page exists
     */
    public boolean hasNextPage() {
        return isElementDisplayed(nextPageLink);
    }

    /**
     * Click next page link.
     * @return WikipediaSearchResultsPage
     */
    public WikipediaSearchResultsPage clickNextPage() {
        click(nextPageLink);
        return new WikipediaSearchResultsPage(driver);
    }

    /**
     * Check if previous page link is available.
     * @return true if previous page exists
     */
    public boolean hasPreviousPage() {
        return isElementDisplayed(prevPageLink);
    }

    /**
     * Click previous page link.
     * @return WikipediaSearchResultsPage
     */
    public WikipediaSearchResultsPage clickPreviousPage() {
        click(prevPageLink);
        return new WikipediaSearchResultsPage(driver);
    }

    /**
     * Check if no results message is displayed.
     * @return true if no results found
     */
    public boolean isNoResultsMessageDisplayed() {
        return isElementDisplayed(noResultsMessage);
    }

    /**
     * Get the search query from input field.
     * @return search query
     */
    public String getSearchQuery() {
        return searchInput.getAttribute("value");
    }

    /**
     * Check if result title contains expected text.
     * @param expectedText text to check
     * @return true if any result contains the text
     */
    public boolean anyResultContains(String expectedText) {
        return getSearchResultTitles().stream()
                .anyMatch(title -> title.toLowerCase().contains(expectedText.toLowerCase()));
    }

    /**
     * Get first result title.
     * @return first result title or empty string
     */
    public String getFirstResultTitle() {
        if (!searchResultLinks.isEmpty()) {
            return searchResultLinks.get(0).getText();
        }
        return "";
    }
}
