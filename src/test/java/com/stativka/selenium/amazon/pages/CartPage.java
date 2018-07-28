package com.stativka.selenium.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static java.lang.Integer.parseInt;
import static org.junit.Assert.assertTrue;

public class CartPage extends BasePage<CartPage> {

	private final NavBarPageBlock navBar;

	@FindBy(css = "#gutterCartViewForm .sc-subtotal")
	private WebElement proceedToCheckoutSubTotal;

	@FindBy(id = "sc-subtotal-label-activecart")
	private WebElement itemsSubTotal;

	@FindBy(name = "quantity")
	private List<WebElement> quantitySelects;

	CartPage(WebDriver driver, NavBarPageBlock navBar) {
		super(driver);
		this.navBar = navBar;
	}

	public int getQuantitySelectorsItemsCount() {
		return quantitySelects
			.stream()
			.map(webElement -> parseInt(new Select(webElement).getFirstSelectedOption().getText().trim()))
			.reduce(0, (prev, next) -> prev + next);
	}

	public int getProceedToCheckoutSubTotalItemsCount() {
		return getItemsCountFromSubTotalString(proceedToCheckoutSubTotal.getText());
	}

	public int getItemsSubTotalItemsCount() {
		return getItemsCountFromSubTotalString(itemsSubTotal.getText());
	}

	@Override
	protected void load() {
		navBar.goToCart();
	}

	@Override
	protected void isLoaded() throws Error {
		assertTrue("Cart is not loaded", driver.getCurrentUrl().contains("/cart/"));
	}

	private int getItemsCountFromSubTotalString(String subTotal) {
		return Character.getNumericValue(subTotal.charAt(subTotal.indexOf('(') + 1));
	}
}
