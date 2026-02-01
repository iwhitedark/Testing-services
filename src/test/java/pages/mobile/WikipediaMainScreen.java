package pages.mobile;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page Object for Wikipedia Android app main screen.
 */
public class WikipediaMainScreen extends MobileBasePage {

    // Search elements
    @FindBy(id = "org.wikipedia:id/search_container")
    private WebElement searchContainer;

    @FindBy(id = "org.wikipedia:id/search_src_text")
    private WebElement searchInput;

    // Skip/Continue buttons for onboarding
    @FindBy(id = "org.wikipedia:id/fragment_onboarding_skip_button")
    private WebElement skipButton;

    @FindBy(id = "org.wikipedia:id/fragment_onboarding_forward_button")
    private WebElement forwardButton;

    // Main page elements
    @FindBy(id = "org.wikipedia:id/main_toolbar_wordmark")
    private WebElement wikipediaWordmark;

    @FindBy(id = "org.wikipedia:id/view_announcement_text")
    private WebElement announcementText;

    // Navigation tabs
    @FindBy(id = "org.wikipedia:id/nav_tab_explore")
    private WebElement exploreTab;

    @FindBy(id = "org.wikipedia:id/nav_tab_reading_lists")
    private WebElement savedTab;

    @FindBy(id = "org.wikipedia:id/nav_tab_search")
    private WebElement searchTab;

    @FindBy(id = "org.wikipedia:id/nav_tab_edits")
    private WebElement editsTab;

    @FindBy(id = "org.wikipedia:id/nav_more_container")
    private WebElement moreTab;

    // Feed items
    @FindBy(id = "org.wikipedia:id/view_card_header_title")
    private List<WebElement> feedCardTitles;

    // Locators
    private final By searchContainerLocator = By.id("org.wikipedia:id/search_container");
    private final By feedCardsLocator = By.id("org.wikipedia:id/view_list_card_list");
    private final By onboardingLocator = By.id("org.wikipedia:id/fragment_onboarding_skip_button");

    public WikipediaMainScreen(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Skip onboarding if present.
     * @return this screen
     */
    public WikipediaMainScreen skipOnboardingIfPresent() {
        try {
            if (isElementDisplayed(onboardingLocator)) {
                click(skipButton);
                Thread.sleep(500);
            }
        } catch (Exception e) {
            // Onboarding not present
        }
        return this;
    }

    /**
     * Wait for main screen to load.
     * @return this screen
     */
    public WikipediaMainScreen waitForMainScreen() {
        skipOnboardingIfPresent();
        waitForElementVisible(searchContainerLocator);
        return this;
    }

    /**
     * Check if main screen is loaded.
     * @return true if loaded
     */
    public boolean isMainScreenLoaded() {
        return isElementDisplayed(searchContainerLocator);
    }

    /**
     * Click on search container to open search.
     * @return WikipediaSearchScreen
     */
    public WikipediaSearchScreen clickSearch() {
        click(searchContainer);
        return new WikipediaSearchScreen(driver);
    }

    /**
     * Click on search tab in navigation.
     * @return WikipediaSearchScreen
     */
    public WikipediaSearchScreen clickSearchTab() {
        click(searchTab);
        return new WikipediaSearchScreen(driver);
    }

    /**
     * Check if Wikipedia wordmark is displayed.
     * @return true if displayed
     */
    public boolean isWordmarkDisplayed() {
        return isElementDisplayed(wikipediaWordmark);
    }

    /**
     * Click on Explore tab.
     * @return this screen
     */
    public WikipediaMainScreen clickExploreTab() {
        click(exploreTab);
        return this;
    }

    /**
     * Click on Saved tab.
     * @return this screen
     */
    public WikipediaMainScreen clickSavedTab() {
        click(savedTab);
        return this;
    }

    /**
     * Click on More tab.
     * @return this screen
     */
    public WikipediaMainScreen clickMoreTab() {
        click(moreTab);
        return this;
    }

    /**
     * Check if Explore tab is displayed.
     * @return true if displayed
     */
    public boolean isExploreTabDisplayed() {
        return isElementDisplayed(exploreTab);
    }

    /**
     * Check if Saved tab is displayed.
     * @return true if displayed
     */
    public boolean isSavedTabDisplayed() {
        return isElementDisplayed(savedTab);
    }

    /**
     * Check if Search tab is displayed.
     * @return true if displayed
     */
    public boolean isSearchTabDisplayed() {
        return isElementDisplayed(searchTab);
    }

    /**
     * Check if feed cards are displayed.
     * @return true if at least one card is displayed
     */
    public boolean hasFeedCards() {
        return isElementDisplayed(feedCardsLocator);
    }

    /**
     * Get number of visible feed card titles.
     * @return count of cards
     */
    public int getFeedCardTitlesCount() {
        return feedCardTitles.size();
    }

    /**
     * Scroll down in the feed.
     */
    public void scrollFeed() {
        scrollDown();
    }

    /**
     * Get text from first feed card title.
     * @return card title text
     */
    public String getFirstFeedCardTitle() {
        if (!feedCardTitles.isEmpty()) {
            return getText(feedCardTitles.get(0));
        }
        return "";
    }

    /**
     * Check if announcement text is displayed.
     * @return true if displayed
     */
    public boolean isAnnouncementDisplayed() {
        return isElementDisplayed(announcementText);
    }

    /**
     * Navigate back.
     */
    public void goBack() {
        pressBack();
    }
}
