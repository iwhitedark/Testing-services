package tests.mobile;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.mobile.WikipediaArticleScreen;
import pages.mobile.WikipediaMainScreen;
import pages.mobile.WikipediaSearchScreen;

/**
 * Mobile test class for Wikipedia Android application.
 * Contains test scenarios for main screen, search, and article viewing.
 */
public class WikipediaMobileTest extends BaseMobileTest {

    private WikipediaMainScreen mainScreen;

    @BeforeMethod
    public void initScreens() {
        mainScreen = new WikipediaMainScreen(driver);
        mainScreen.waitForMainScreen();
    }

    // ==================== Test Scenario 1: Main Screen Display ====================

    @Test(priority = 1, description = "Verify main screen loads correctly with search container")
    public void testMainScreenLoadsCorrectly() {
        Assert.assertTrue(mainScreen.isMainScreenLoaded(),
                "Main screen should be loaded with search container");
    }

    @Test(priority = 2, description = "Verify navigation tabs are displayed")
    public void testNavigationTabsDisplayed() {
        Assert.assertTrue(mainScreen.isExploreTabDisplayed(),
                "Explore tab should be displayed");
        Assert.assertTrue(mainScreen.isSavedTabDisplayed(),
                "Saved tab should be displayed");
        Assert.assertTrue(mainScreen.isSearchTabDisplayed(),
                "Search tab should be displayed");
    }

    // ==================== Test Scenario 2: Search Functionality ====================

    @Test(priority = 3, description = "Verify search screen opens when clicking search")
    public void testSearchScreenOpens() {
        WikipediaSearchScreen searchScreen = mainScreen.clickSearch();

        Assert.assertTrue(searchScreen.isSearchScreenLoaded(),
                "Search screen should be loaded");
    }

    @Test(priority = 4, description = "Verify search returns results for valid query")
    public void testSearchReturnsResults() {
        WikipediaSearchScreen searchScreen = mainScreen.clickSearch();
        searchScreen.search("Java programming");

        Assert.assertTrue(searchScreen.hasSearchResults(),
                "Search should return results for 'Java programming'");
        Assert.assertTrue(searchScreen.getSearchResultsCount() > 0,
                "Should have at least one search result");
    }

    @DataProvider(name = "searchQueries")
    public Object[][] searchQueriesProvider() {
        return new Object[][] {
                {"Python"},
                {"Einstein"},
                {"Moscow"}
        };
    }

    @Test(priority = 5, dataProvider = "searchQueries",
          description = "Verify search works for multiple queries")
    public void testSearchWithMultipleQueries(String query) {
        WikipediaSearchScreen searchScreen = mainScreen.clickSearch();
        searchScreen.search(query);

        Assert.assertTrue(searchScreen.hasSearchResults(),
                "Search should return results for '" + query + "'");
    }

    // ==================== Test Scenario 3: Open Article and Verify Title ====================

    @Test(priority = 6, description = "Verify clicking search result opens article")
    public void testOpenArticleFromSearch() {
        WikipediaSearchScreen searchScreen = mainScreen.clickSearch();
        searchScreen.search("Albert Einstein");

        WikipediaArticleScreen articleScreen = searchScreen.clickFirstResult();
        articleScreen.waitForArticleToLoad();

        Assert.assertTrue(articleScreen.isArticleLoaded(),
                "Article should be loaded after clicking search result");
    }

    @Test(priority = 7, description = "Verify article title matches search query")
    public void testArticleTitleMatchesSearch() {
        WikipediaSearchScreen searchScreen = mainScreen.clickSearch();
        searchScreen.search("Python programming language");

        String firstResultTitle = searchScreen.getFirstResultTitle();
        WikipediaArticleScreen articleScreen = searchScreen.clickFirstResult();
        articleScreen.waitForArticleToLoad();

        Assert.assertTrue(articleScreen.isArticleLoaded(),
                "Article should be loaded");
    }

    @Test(priority = 8, description = "Verify article toolbar is displayed")
    public void testArticleToolbarDisplayed() {
        WikipediaSearchScreen searchScreen = mainScreen.clickSearch();
        searchScreen.search("Machine learning");

        WikipediaArticleScreen articleScreen = searchScreen.clickFirstResult();
        articleScreen.waitForArticleToLoad();

        Assert.assertTrue(articleScreen.isToolbarDisplayed(),
                "Article toolbar should be displayed");
    }

    // ==================== Test Scenario 4: Article Scrolling ====================

    @Test(priority = 9, description = "Verify article can be scrolled")
    public void testArticleScrolling() {
        WikipediaSearchScreen searchScreen = mainScreen.clickSearch();
        searchScreen.search("World War II");

        WikipediaArticleScreen articleScreen = searchScreen.clickFirstResult();
        articleScreen.waitForArticleToLoad();

        Assert.assertTrue(articleScreen.isArticleLoaded(),
                "Article should be loaded before scrolling");

        // Scroll down multiple times
        articleScreen.scrollArticleDown();
        articleScreen.scrollArticleDown();

        Assert.assertTrue(articleScreen.isArticleLoaded(),
                "Article should still be loaded after scrolling");
    }

    // ==================== Test Scenario 5: Navigation Between Screens ====================

    @Test(priority = 10, description = "Verify navigation back from article to search")
    public void testNavigationBackFromArticle() {
        WikipediaSearchScreen searchScreen = mainScreen.clickSearch();
        searchScreen.search("Computer science");

        WikipediaArticleScreen articleScreen = searchScreen.clickFirstResult();
        articleScreen.waitForArticleToLoad();

        Assert.assertTrue(articleScreen.isArticleLoaded(),
                "Article should be loaded");

        articleScreen.goBack();

        // Should be back on search screen
        Assert.assertTrue(searchScreen.isSearchScreenLoaded() || mainScreen.isMainScreenLoaded(),
                "Should navigate back to search or main screen");
    }

    @Test(priority = 11, description = "Verify search from article toolbar")
    public void testSearchFromArticleToolbar() {
        WikipediaSearchScreen searchScreen = mainScreen.clickSearch();
        searchScreen.search("Mathematics");

        WikipediaArticleScreen articleScreen = searchScreen.clickFirstResult();
        articleScreen.waitForArticleToLoad();

        WikipediaSearchScreen newSearchScreen = articleScreen.clickToolbarSearch();

        Assert.assertTrue(newSearchScreen.isSearchScreenLoaded(),
                "New search screen should open from article toolbar");
    }

    // ==================== Test Scenario 6: Search Result Content Verification ====================

    @Test(priority = 12, description = "Verify search results contain expected keyword")
    public void testSearchResultsContainKeyword() {
        WikipediaSearchScreen searchScreen = mainScreen.clickSearch();
        searchScreen.search("Solar System");

        Assert.assertTrue(searchScreen.hasSearchResults(),
                "Should have search results");

        String firstResult = searchScreen.getFirstResultTitle();
        Assert.assertFalse(firstResult.isEmpty(),
                "First result title should not be empty");
    }

    // ==================== Test Scenario 7: Multiple Article Navigation ====================

    @Test(priority = 13, description = "Verify opening multiple articles in sequence")
    public void testMultipleArticleNavigation() {
        // First article
        WikipediaSearchScreen searchScreen = mainScreen.clickSearch();
        searchScreen.search("Physics");

        WikipediaArticleScreen articleScreen = searchScreen.clickFirstResult();
        articleScreen.waitForArticleToLoad();
        Assert.assertTrue(articleScreen.isArticleLoaded(), "First article should load");

        // Go back and search for second article
        articleScreen.goBack();
        searchScreen.waitForSearchScreen();
        searchScreen.clearSearch();
        searchScreen.search("Chemistry");

        articleScreen = searchScreen.clickFirstResult();
        articleScreen.waitForArticleToLoad();
        Assert.assertTrue(articleScreen.isArticleLoaded(), "Second article should load");
    }

    // ==================== Test Scenario 8: Save Article Functionality ====================

    @Test(priority = 14, description = "Verify save button is available on article")
    public void testSaveButtonAvailable() {
        WikipediaSearchScreen searchScreen = mainScreen.clickSearch();
        searchScreen.search("History");

        WikipediaArticleScreen articleScreen = searchScreen.clickFirstResult();
        articleScreen.waitForArticleToLoad();

        Assert.assertTrue(articleScreen.isSaveButtonDisplayed(),
                "Save button should be displayed on article page");
    }
}
