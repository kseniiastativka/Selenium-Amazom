package com.stativka.selenium.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static org.junit.Assert.assertTrue;

public class CartPage extends BasePage<CartPage> {
	private static final String quantityNameAttribute = "quantity";

	private final NavBarPageBlock navBar;

	@FindBy(css = "#gutterCartViewForm .sc-subtotal")
	private WebElement proceedToCheckoutSubTotal;

	@FindBy(id = "sc-subtotal-label-activecart")
	private WebElement itemsSubTotal;

	@FindBy(id = "sc-subtotal-amount-activecart")
	private WebElement itemsPricesSubTotal;

	@FindBy(css = "[data-name=\"Active Items\"] .sc-list-item")
	private List<WebElement> items;

	@FindBy(name = quantityNameAttribute)
	private List<WebElement> quantitySelects;

	@FindBy(css = "#gutterCartViewForm .sc-price")
	private WebElement proceedToCheckoutPrice;

	CartPage(WebDriver driver, NavBarPageBlock navBar) {
		super(driver);
		this.navBar = navBar;
	}

	public int getQuantitySelectorsItemsCount() {
		return quantitySelects
			.stream()
			.map(this::getQuantityFromSelect)
			.reduce(0, (prev, next) -> prev + next);
	}

	public double getItemsPriceSum() {
		return items
			.stream()
			.map(item -> {
				double price = getItemsPriceFromSubTotalString(item.findElement(By.className("sc-price")).getText());
				int quantity = getQuantityFromSelect(item.findElement(By.name(quantityNameAttribute)));
				return price * quantity;
			})
			.reduce(0.0, (prev, next) -> prev + next);
	}

	public int getProceedToCheckoutSubTotalItemsCount() {
		return getItemsCountFromSubTotalString(proceedToCheckoutSubTotal.getText());
	}

	public int getItemsSubTotalItemsCount() {
		return getItemsCountFromSubTotalString(itemsSubTotal.getText());
	}

	public double getItemsSubTotalPrice() {
		return getItemsPriceFromSubTotalString(itemsPricesSubTotal.getText());
	}

	public double getProceedToCheckoutItemsPrice() {
		return getItemsPriceFromSubTotalString(proceedToCheckoutPrice.getText());
	}

	public CartPage setItemQuantity(String textInItemLink, int quantity) {

		for (WebElement item : items) {
			boolean itemIsFound = item.findElement(By.className("sc-product-title")).getText().contains(textInItemLink);

			if (itemIsFound) {
				WebElement select = item.findElement(By.name(quantityNameAttribute));
				new Select(select).selectByValue(Integer.toString(quantity));
				// quantity change takes some time:
				wait.until(ExpectedConditions.invisibilityOf(item.findElement(By.className("sc-list-item-overwrap"))));
				break;
			}
		}

		return this;
	}

	@Override
	protected void load() {
		navBar.goToCart();
	}

	@Override
	protected void isLoaded() throws Error {
		assertTrue("Cart is not loaded", getUrl().contains("/cart/"));
	}

	private int getItemsCountFromSubTotalString(String subTotal) {
		return parseInt(
			subTotal
				.substring(subTotal.indexOf('(') + 1, subTotal.lastIndexOf(')'))
				.replaceAll("[^\\d]+", "")
		);
	}

	private double getItemsPriceFromSubTotalString(String subTotalPrice) {
		return parseFloat(subTotalPrice.replaceAll("[^\\d.]+", ""));
	}

	private int getQuantityFromSelect(WebElement select) {
		return parseInt(new Select(select).getFirstSelectedOption().getText().trim());
	}
}
