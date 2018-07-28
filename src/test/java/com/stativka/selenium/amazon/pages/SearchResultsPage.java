package com.stativka.selenium.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.annotation.Nullable;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SearchResultsPage extends BasePage<SearchResultsPage> {
	private static final String foundItemsClassName = "s-result-item";
	private static final By itemTitleLocator = By.className("s-access-title");
	private static final By itemProfileLinkLocator = By.className("s-access-detail-page");
	private final NavBarPageBlock navBar;
	private String searchTerm;

	@FindBy(className = foundItemsClassName)
	private List<WebElement> foundItems;

	@FindBy(id = "noResultsTitle")
	private WebElement noResultsMessage;

	SearchResultsPage(WebDriver driver, NavBarPageBlock navBar) {
		super(driver);
		this.navBar = navBar;
	}

	public SearchResultsPage(WebDriver driver, NavBarPageBlock navBar, String searchTerm) {
		this(driver, navBar);
		this.searchTerm = searchTerm;
	}

	@Override
	protected void load() {
		navBar.search(searchTerm == null ? "" : searchTerm);
	}

	@Override
	protected void isLoaded() throws Error {
		assertTrue(
			"Search results page is not loaded",
			foundItems.size() > 0 || noResultsMessage.isDisplayed()
		);
	}

	@Nullable
	public ItemPage goToItemByText(String text) {
		WebElement itemLink = findItemLinkByText(text);

		if (itemLink != null) {
			itemLink.click();
			return new ItemPage(driver, navBar).get();
		}

		return null;
	}

	@Nullable
	private WebElement findItemLinkByText(String text) {
		for (WebElement item : foundItems) {
			String title = item.findElement(itemTitleLocator).getText().toLowerCase();

			if (title.contains(text.toLowerCase())) {
				return item.findElement(itemProfileLinkLocator);
			}
		}

		return null;
	}
}
