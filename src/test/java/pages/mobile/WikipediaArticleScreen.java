package pages.mobile;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page Object for Wikipedia Android app article screen.
 */
public class WikipediaArticleScreen extends MobileBasePage {

    // Article title
    @FindBy(id = "org.wikipedia:id/view_page_title_text")
    private WebElement articleTitle;

    // Article content
    @FindBy(id = "org.wikipedia:id/page_web_view")
    private WebElement articleWebView;

    @FindBy(id = "org.wikipedia:id/page_contents_container")
    private WebElement articleContentsContainer;

    // Toolbar elements
    @FindBy(id = "org.wikipedia:id/page_toolbar")
    private WebElement pageToolbar;

    @FindBy(id = "org.wikipedia:id/page_toolbar_button_search")
    private WebElement toolbarSearchButton;

    @FindBy(id = "org.wikipedia:id/page_toolbar_button_tabs")
    private WebElement toolbarTabsButton;

    @FindBy(id = "org.wikipedia:id/page_toolbar_button_show_overflow_menu")
    private WebElement toolbarOverflowMenu;

    // Save/bookmark button
    @FindBy(id = "org.wikipedia:id/page_save")
    private WebElement saveButton;

    // Table of contents
    @FindBy(id = "org.wikipedia:id/page_toc_button")
    private WebElement tocButton;

    @FindBy(id = "org.wikipedia:id/page_toc_list")
    private WebElement tocList;

    @FindBy(id = "org.wikipedia:id/page_toc_item_text")
    private List<WebElement> tocItems;

    // Language button
    @FindBy(id = "org.wikipedia:id/page_language")
    private WebElement languageButton;

    // Back button
    @FindBy(className = "android.widget.ImageButton")
    private WebElement backButton;

    // Article header
    @FindBy(id = "org.wikipedia:id/view_page_header_image")
    private WebElement headerImage;

    // Locators
    private final By articleTitleLocator = By.id("org.wikipedia:id/view_page_title_text");
    private final By pageToolbarLocator = By.id("org.wikipedia:id/page_toolbar");
    private final By articleWebViewLocator = By.id("org.wikipedia:id/page_web_view");
    private final By tocListLocator = By.id("org.wikipedia:id/page_toc_list");

    public WikipediaArticleScreen(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Wait for article to load.
     * @return this screen
     */
    public WikipediaArticleScreen waitForArticleToLoad() {
        try {
            waitForElementVisible(pageToolbarLocator);
            Thread.sleep(1000); // Wait for content to render
        } catch (Exception e) {
            // Article may load differently
        }
        return this;
    }

    /**
     * Check if article is loaded.
     * @return true if loaded
     */
    public boolean isArticleLoaded() {
        return isElementDisplayed(pageToolbarLocator) || isElementDisplayed(articleWebViewLocator);
    }

    /**
     * Get article title text.
     * @return article title
     */
    public String getArticleTitle() {
        try {
            waitForElementVisible(articleTitle);
            return getText(articleTitle);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Check if article title is displayed.
     * @return true if displayed
     */
    public boolean isArticleTitleDisplayed() {
        return isElementDisplayed(articleTitleLocator);
    }

    /**
     * Check if article title matches expected.
     * @param expectedTitle expected title
     * @return true if matches
     */
    public boolean isTitleMatch(String expectedTitle) {
        String actualTitle = getArticleTitle();
        return actualTitle.equalsIgnoreCase(expectedTitle);
    }

    /**
     * Check if article title contains expected text.
     * @param expectedText expected text
     * @return true if contains
     */
    public boolean titleContains(String expectedText) {
        String actualTitle = getArticleTitle();
        return actualTitle.toLowerCase().contains(expectedText.toLowerCase());
    }

    /**
     * Click on table of contents button.
     * @return this screen
     */
    public WikipediaArticleScreen openTableOfContents() {
        click(tocButton);
        waitForElementVisible(tocListLocator);
        return this;
    }

    /**
     * Check if table of contents is displayed.
     * @return true if displayed
     */
    public boolean isTableOfContentsDisplayed() {
        return isElementDisplayed(tocListLocator);
    }

    /**
     * Get table of contents items count.
     * @return count of TOC items
     */
    public int getTocItemsCount() {
        return tocItems.size();
    }

    /**
     * Click on TOC item by index.
     * @param index item index
     * @return this screen
     */
    public WikipediaArticleScreen clickTocItem(int index) {
        if (index < tocItems.size()) {
            click(tocItems.get(index));
        }
        return this;
    }

    /**
     * Close table of contents.
     * @return this screen
     */
    public WikipediaArticleScreen closeTableOfContents() {
        pressBack();
        return this;
    }

    /**
     * Click save button to bookmark article.
     * @return this screen
     */
    public WikipediaArticleScreen clickSave() {
        click(saveButton);
        return this;
    }

    /**
     * Check if save button is displayed.
     * @return true if displayed
     */
    public boolean isSaveButtonDisplayed() {
        return isElementDisplayed(saveButton);
    }

    /**
     * Click language button to change article language.
     * @return this screen
     */
    public WikipediaArticleScreen clickLanguageButton() {
        click(languageButton);
        return this;
    }

    /**
     * Check if language button is displayed.
     * @return true if displayed
     */
    public boolean isLanguageButtonDisplayed() {
        return isElementDisplayed(languageButton);
    }

    /**
     * Click search button in toolbar.
     * @return WikipediaSearchScreen
     */
    public WikipediaSearchScreen clickToolbarSearch() {
        click(toolbarSearchButton);
        return new WikipediaSearchScreen(driver);
    }

    /**
     * Click overflow menu button.
     * @return this screen
     */
    public WikipediaArticleScreen clickOverflowMenu() {
        click(toolbarOverflowMenu);
        return this;
    }

    /**
     * Scroll article down.
     */
    public void scrollArticleDown() {
        scrollDown();
    }

    /**
     * Scroll to specific text in article.
     * @param text text to find
     */
    public void scrollToText(String text) {
        scrollToText(text);
    }

    /**
     * Check if header image is displayed.
     * @return true if displayed
     */
    public boolean hasHeaderImage() {
        return isElementDisplayed(headerImage);
    }

    /**
     * Check if toolbar is displayed.
     * @return true if displayed
     */
    public boolean isToolbarDisplayed() {
        return isElementDisplayed(pageToolbar);
    }

    /**
     * Go back to previous screen.
     * @return WikipediaSearchScreen or main screen
     */
    public void goBack() {
        pressBack();
    }

    /**
     * Go back to main screen.
     * @return WikipediaMainScreen
     */
    public WikipediaMainScreen goBackToMain() {
        pressBack();
        pressBack();
        return new WikipediaMainScreen(driver);
    }
}
