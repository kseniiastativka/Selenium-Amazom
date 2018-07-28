package com.stativka.selenium.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static java.lang.Integer.parseInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Page object for elements available everywhere in the app
 */
public class NavBarPageBlock extends BasePage<NavBarPageBlock> {
	private static final String url = "https://www.amazon.com/";
	private static final String searchInputId = "twotabsearchtextbox";
	private static final String cartLinkId = "nav-cart";
	private static final String cartItemsCountId = "nav-cart-count";

	@FindBy(id = searchInputId)
	private WebElement searchInput;

	@FindBy(id = cartLinkId)
	private WebElement cartLink;

	@FindBy(id = cartItemsCountId)
	private WebElement cartItemsCount;

	public NavBarPageBlock(WebDriver driver) {
		super(driver);
	}

	public SearchResultsPage search(String text) {
		searchInput.clear();
		searchInput.sendKeys(text);
		searchInput.submit();
		return new SearchResultsPage(driver, this).get();
	}

	public CartPage goToCart() {
		cartLink.click();
		return new CartPage(driver, this).get();
	}

	public int getCartItemsCount() {
		return parseInt(cartItemsCount.getText());
	}

	@Override
	protected void load() {
		driver.get(url);
	}

	@Override
	protected void isLoaded() throws Error {
		String currentUrl = driver.getCurrentUrl();
		assertTrue("Amazon home page is not loaded", currentUrl.startsWith(url));
	}
}
