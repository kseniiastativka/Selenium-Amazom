package com.stativka.selenium.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.annotation.Nullable;
import java.util.List;

public class SearchResults extends BasePage<SearchResults> {
	private static final String foundItemsClassName = "s-result-item";
	private static final By itemTitleLocator = By.className("s-access-title");
	private static final By itemProfileLinkLocator = By.className("s-access-detail-page");
	private final MainPage mainPage;
	private String searchTerm;

	@FindBy(className = foundItemsClassName)
	private List<WebElement> foundItems;

	SearchResults(WebDriver driver, MainPage mainPage) {
		super(driver);
		this.mainPage = mainPage;
	}

	public SearchResults(WebDriver driver, MainPage mainPage, String searchTerm) {
		this(driver, mainPage);
		this.searchTerm = searchTerm;
	}

	@Override
	protected void load() {
		mainPage.search(searchTerm == null ? "" : searchTerm);
	}

	@Override
	protected void isLoaded() throws Error {
		// TODO
	}

	@Nullable
	public ItemPage goToItemByText(String text) {
		WebElement itemLink = findItemLinkByText(text);

		if (itemLink != null) {
			itemLink.click();
			return new ItemPage(driver, mainPage).get();
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
