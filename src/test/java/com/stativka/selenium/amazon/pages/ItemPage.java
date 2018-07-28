package com.stativka.selenium.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static org.junit.Assert.assertTrue;

public class ItemPage extends BasePage<ItemPage> {
	private final NavBarPageBlock navBar;

	@FindBy(id = "add-to-cart-button")
	private WebElement addToCartButton;

	@FindBy(id = "quantity")
	private WebElement quantitySelect;

	ItemPage(WebDriver driver, NavBarPageBlock navBar) {
		super(driver);
		this.navBar = navBar;
	}

	public void addToCart(int quantity) {
		new Select(quantitySelect).selectByValue(Integer.toString(quantity));
		addToCartButton.click();
	}

	@Override
	protected void load() {
		navBar.get();
	}

	@Override
	protected void isLoaded() throws Error {
		assertTrue(
			"Item page is not loaded since Add to Cart button is not present",
			addToCartButton.isDisplayed()
		);
	}
}
