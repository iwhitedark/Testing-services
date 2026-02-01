package pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object for Wikipedia article page.
 */
public class WikipediaArticlePage extends BasePage {

    // Article title
    @FindBy(id = "firstHeading")
    private WebElement articleTitle;

    // Article content
    @FindBy(id = "mw-content-text")
    private WebElement articleContent;

    @FindBy(css = "#mw-content-text p")
    private List<WebElement> articleParagraphs;

    // Table of contents
    @FindBy(id = "toc")
    private WebElement tableOfContents;

    @FindBy(css = "#toc ul li a")
    private List<WebElement> tocLinks;

    // Navigation elements
    @FindBy(css = ".mw-logo")
    private WebElement logo;

    @FindBy(name = "search")
    private WebElement searchInput;

    // Language links
    @FindBy(css = "#p-lang-btn")
    private WebElement languageButton;

    @FindBy(css = ".interlanguage-link a")
    private List<WebElement> languageLinks;

    // Edit link
    @FindBy(css = "#ca-edit a")
    private WebElement editLink;

    // Categories
    @FindBy(id = "mw-normal-catlinks")
    private WebElement categoriesSection;

    @FindBy(css = "#mw-normal-catlinks a")
    private List<WebElement> categoryLinks;

    // References
    @FindBy(css = ".reference")
    private List<WebElement> references;

    // Infobox
    @FindBy(css = ".infobox")
    private WebElement infobox;

    // Locators
    private final By headingSectionLocator = By.cssSelector(".mw-heading");
    private final By externalLinksLocator = By.cssSelector(".external");

    public WikipediaArticlePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Check if article page is loaded.
     * @return true if article is loaded
     */
    public boolean isPageLoaded() {
        return isElementDisplayed(articleTitle);
    }

    /**
     * Get article title text.
     * @return article title
     */
    public String getArticleTitle() {
        waitForElementVisible(articleTitle);
        return articleTitle.getText();
    }

    /**
     * Check if article title matches expected.
     * @param expectedTitle expected title
     * @return true if matches
     */
    public boolean isTitleMatch(String expectedTitle) {
        return getArticleTitle().equalsIgnoreCase(expectedTitle);
    }

    /**
     * Check if article title contains expected text.
     * @param expectedText expected text
     * @return true if contains
     */
    public boolean titleContains(String expectedText) {
        return getArticleTitle().toLowerCase().contains(expectedText.toLowerCase());
    }

    /**
     * Get first paragraph text.
     * @return first paragraph text
     */
    public String getFirstParagraphText() {
        if (!articleParagraphs.isEmpty()) {
            for (WebElement p : articleParagraphs) {
                String text = p.getText().trim();
                if (!text.isEmpty() && text.length() > 50) {
                    return text;
                }
            }
        }
        return "";
    }

    /**
     * Check if article contains specific text.
     * @param text text to search
     * @return true if content contains text
     */
    public boolean articleContainsText(String text) {
        return articleContent.getText().toLowerCase().contains(text.toLowerCase());
    }

    /**
     * Check if table of contents is present.
     * @return true if TOC exists
     */
    public boolean hasTableOfContents() {
        return isElementDisplayed(tableOfContents);
    }

    /**
     * Get table of contents links text.
     * @return list of TOC section names
     */
    public List<String> getTocSectionNames() {
        return tocLinks.stream()
                .map(WebElement::getText)
                .filter(text -> !text.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Click on table of contents link by section name.
     * @param sectionName section name to click
     */
    public void clickTocSection(String sectionName) {
        for (WebElement link : tocLinks) {
            if (link.getText().toLowerCase().contains(sectionName.toLowerCase())) {
                click(link);
                return;
            }
        }
        throw new RuntimeException("TOC section not found: " + sectionName);
    }

    /**
     * Check if infobox is present.
     * @return true if infobox exists
     */
    public boolean hasInfobox() {
        return isElementDisplayed(infobox);
    }

    /**
     * Get number of references in article.
     * @return count of references
     */
    public int getReferencesCount() {
        return references.size();
    }

    /**
     * Check if article has references.
     * @return true if article has references
     */
    public boolean hasReferences() {
        return !references.isEmpty();
    }

    /**
     * Get categories of the article.
     * @return list of category names
     */
    public List<String> getCategories() {
        if (isElementDisplayed(categoriesSection)) {
            return categoryLinks.stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    /**
     * Check if categories section is present.
     * @return true if categories exist
     */
    public boolean hasCategories() {
        return isElementDisplayed(categoriesSection);
    }

    /**
     * Get number of section headings.
     * @return count of headings
     */
    public int getSectionHeadingsCount() {
        List<WebElement> headings = driver.findElements(headingSectionLocator);
        return headings.size();
    }

    /**
     * Scroll to bottom of article.
     */
    public void scrollToBottom() {
        scrollToElement(categoriesSection);
    }

    /**
     * Navigate back to main page via logo.
     * @return WikipediaEnglishHomePage
     */
    public WikipediaEnglishHomePage clickLogo() {
        click(logo);
        return new WikipediaEnglishHomePage(driver);
    }

    /**
     * Perform search from article page.
     * @param query search query
     * @return WikipediaSearchResultsPage
     */
    public WikipediaSearchResultsPage search(String query) {
        enterText(searchInput, query);
        searchInput.submit();
        return new WikipediaSearchResultsPage(driver);
    }

    /**
     * Check if language button is present.
     * @return true if language button exists
     */
    public boolean hasLanguageOptions() {
        return isElementDisplayed(languageButton);
    }

    /**
     * Get number of available languages.
     * @return count of languages
     */
    public int getAvailableLanguagesCount() {
        return languageLinks.size();
    }

    /**
     * Check if edit link is present.
     * @return true if edit link exists
     */
    public boolean hasEditLink() {
        return isElementDisplayed(editLink);
    }

    /**
     * Get current URL of the article.
     * @return article URL
     */
    public String getArticleUrl() {
        return getCurrentUrl();
    }

    /**
     * Check if URL contains expected article name.
     * @param articleName expected article name in URL
     * @return true if URL contains article name
     */
    public boolean urlContainsArticleName(String articleName) {
        return getCurrentUrl().toLowerCase().contains(articleName.toLowerCase().replace(" ", "_"));
    }
}
