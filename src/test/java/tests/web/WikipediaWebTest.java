package tests.web;

import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.web.WikipediaArticlePage;
import pages.web.WikipediaEnglishHomePage;
import pages.web.WikipediaHomePage;
import pages.web.WikipediaSearchResultsPage;

/**
 * Web test class for Wikipedia website.
 * Contains test scenarios for main page, search, navigation, and article pages.
 */
public class WikipediaWebTest extends BaseWebTest {

    private WikipediaHomePage homePage;
    private WikipediaEnglishHomePage englishHomePage;

    @BeforeMethod
    public void initPages() {
        homePage = new WikipediaHomePage(driver);
        englishHomePage = new WikipediaEnglishHomePage(driver);
    }

    // ==================== Test Scenario 1: Main Page Elements ====================

    @Test(priority = 1, description = "Verify Wikipedia main page loads correctly with all key elements")
    public void testMainPageLoadsCorrectly() {
        homePage.open(ConfigReader.getWebBaseUrl());

        Assert.assertTrue(homePage.isPageLoaded(), "Main page should be loaded");
        Assert.assertTrue(homePage.isLogoDisplayed(), "Wikipedia logo should be displayed");
        Assert.assertTrue(homePage.isSearchInputDisplayed(), "Search input should be displayed");
        Assert.assertTrue(homePage.isEnglishLinkDisplayed(), "English language link should be displayed");
        Assert.assertTrue(homePage.isRussianLinkDisplayed(), "Russian language link should be displayed");
    }

    @Test(priority = 2, description = "Verify main page has multiple language links")
    public void testMainPageHasLanguageLinks() {
        homePage.open(ConfigReader.getWebBaseUrl());

        int languageLinksCount = homePage.getMainLanguageLinksCount();
        Assert.assertTrue(languageLinksCount >= 5,
                "Main page should have at least 5 main language links, found: " + languageLinksCount);
    }

    // ==================== Test Scenario 2: Search Functionality ====================

    @Test(priority = 3, description = "Verify search returns results for valid query")
    public void testSearchReturnsResults() {
        homePage.open(ConfigReader.getWebBaseUrl());

        WikipediaSearchResultsPage resultsPage = homePage.search("Java programming");

        Assert.assertTrue(resultsPage.hasResults() || resultsPage.isPageLoaded(),
                "Search should return results or navigate to article");
    }

    @Test(priority = 4, description = "Verify search from English Wikipedia works correctly")
    public void testSearchFromEnglishWikipedia() {
        englishHomePage.open(ConfigReader.getWikipediaEnUrl());

        WikipediaSearchResultsPage resultsPage = englishHomePage.search("Albert Einstein");

        Assert.assertTrue(resultsPage.isPageLoaded(), "Search results page should load");
    }

    @DataProvider(name = "searchQueries")
    public Object[][] searchQueriesProvider() {
        return new Object[][] {
                {"Python programming language"},
                {"World War II"},
                {"Solar System"}
        };
    }

    @Test(priority = 5, dataProvider = "searchQueries",
          description = "Verify search works for multiple queries using DataProvider")
    public void testSearchWithMultipleQueries(String query) {
        englishHomePage.open(ConfigReader.getWikipediaEnUrl());

        WikipediaSearchResultsPage resultsPage = englishHomePage.search(query);

        Assert.assertTrue(resultsPage.isPageLoaded(),
                "Search for '" + query + "' should return results");
    }

    // ==================== Test Scenario 3: Navigation ====================

    @Test(priority = 6, description = "Verify clicking English link navigates to English Wikipedia")
    public void testNavigateToEnglishWikipedia() {
        homePage.open(ConfigReader.getWebBaseUrl());

        WikipediaEnglishHomePage engPage = homePage.clickEnglishLink();

        Assert.assertTrue(engPage.getCurrentUrl().contains("en.wikipedia.org"),
                "URL should contain en.wikipedia.org");
        Assert.assertTrue(engPage.getTitle().contains("Wikipedia"),
                "Page title should contain 'Wikipedia'");
    }

    @Test(priority = 7, description = "Verify random article link works")
    public void testRandomArticleNavigation() {
        englishHomePage.open(ConfigReader.getWikipediaEnUrl());

        WikipediaArticlePage articlePage = englishHomePage.clickRandomArticle();

        Assert.assertTrue(articlePage.isPageLoaded(), "Random article should load");
        Assert.assertFalse(articlePage.getArticleTitle().isEmpty(),
                "Article should have a title");
    }

    // ==================== Test Scenario 4: Article Page Elements ====================

    @Test(priority = 8, description = "Verify article page displays correct title")
    public void testArticlePageTitle() {
        englishHomePage.open(ConfigReader.getWikipediaEnUrl());

        WikipediaSearchResultsPage resultsPage = englishHomePage.search("Java programming language");
        WikipediaArticlePage articlePage = resultsPage.clickFirstResult();

        Assert.assertTrue(articlePage.isPageLoaded(), "Article page should load");
        String title = articlePage.getArticleTitle();
        Assert.assertFalse(title.isEmpty(), "Article should have a non-empty title");
    }

    @Test(priority = 9, description = "Verify article contains expected content")
    public void testArticleContainsContent() {
        englishHomePage.open(ConfigReader.getWikipediaEnUrl());

        WikipediaSearchResultsPage resultsPage = englishHomePage.search("Python programming");
        WikipediaArticlePage articlePage = resultsPage.clickFirstResult();

        Assert.assertTrue(articlePage.isPageLoaded(), "Article page should load");
        String firstParagraph = articlePage.getFirstParagraphText();
        Assert.assertFalse(firstParagraph.isEmpty(),
                "Article should have content in the first paragraph");
    }

    @Test(priority = 10, description = "Verify article has references section")
    public void testArticleHasReferences() {
        englishHomePage.open(ConfigReader.getWikipediaEnUrl());

        WikipediaSearchResultsPage resultsPage = englishHomePage.search("Albert Einstein");
        WikipediaArticlePage articlePage = resultsPage.clickFirstResult();

        Assert.assertTrue(articlePage.isPageLoaded(), "Article page should load");
        Assert.assertTrue(articlePage.hasReferences(),
                "Article should have references");
    }

    // ==================== Test Scenario 5: Article Navigation and Structure ====================

    @Test(priority = 11, description = "Verify article URL contains article name")
    public void testArticleUrlContainsName() {
        englishHomePage.open(ConfigReader.getWikipediaEnUrl());

        WikipediaSearchResultsPage resultsPage = englishHomePage.search("Machine learning");
        WikipediaArticlePage articlePage = resultsPage.clickFirstResult();

        Assert.assertTrue(articlePage.isPageLoaded(), "Article page should load");
        String url = articlePage.getArticleUrl();
        Assert.assertTrue(url.contains("wiki/"),
                "Article URL should contain 'wiki/': " + url);
    }

    @Test(priority = 12, description = "Verify article has categories")
    public void testArticleHasCategories() {
        englishHomePage.open(ConfigReader.getWikipediaEnUrl());

        WikipediaSearchResultsPage resultsPage = englishHomePage.search("Computer science");
        WikipediaArticlePage articlePage = resultsPage.clickFirstResult();

        articlePage.scrollToBottom();

        Assert.assertTrue(articlePage.hasCategories(),
                "Article should have categories section");
    }

    // ==================== Test Scenario 6: Cross-Page Search ====================

    @Test(priority = 13, description = "Verify search from article page works")
    public void testSearchFromArticlePage() {
        englishHomePage.open(ConfigReader.getWikipediaEnUrl());

        WikipediaSearchResultsPage resultsPage = englishHomePage.search("Mathematics");
        WikipediaArticlePage articlePage = resultsPage.clickFirstResult();

        Assert.assertTrue(articlePage.isPageLoaded(), "First article should load");

        WikipediaSearchResultsPage newResults = articlePage.search("Physics");

        Assert.assertTrue(newResults.isPageLoaded(),
                "Search from article page should work");
    }

    // ==================== Test Scenario 7: English Wikipedia Main Page Sections ====================

    @Test(priority = 14, description = "Verify English Wikipedia main page has key sections")
    public void testEnglishWikipediaMainPageSections() {
        englishHomePage.open(ConfigReader.getWikipediaEnUrl());

        Assert.assertTrue(englishHomePage.isPageLoaded(), "English Wikipedia should load");
        Assert.assertTrue(englishHomePage.isLogoDisplayed(), "Logo should be displayed");
        Assert.assertTrue(englishHomePage.isSearchInputDisplayed(),
                "Search input should be displayed");
    }

    // ==================== Test Scenario 8: Page Title Verification ====================

    @Test(priority = 15, description = "Verify page titles are correct")
    public void testPageTitles() {
        homePage.open(ConfigReader.getWebBaseUrl());
        String mainPageTitle = homePage.getPageTitle();
        Assert.assertTrue(mainPageTitle.contains("Wikipedia"),
                "Main page title should contain 'Wikipedia': " + mainPageTitle);

        englishHomePage.open(ConfigReader.getWikipediaEnUrl());
        String englishPageTitle = englishHomePage.getTitle();
        Assert.assertTrue(englishPageTitle.contains("Wikipedia"),
                "English page title should contain 'Wikipedia': " + englishPageTitle);
    }
}
