package com.stativka.selenium.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import javax.annotation.Nullable;
import java.util.List;

import static com.stativka.selenium.amazon.helpers.Numbers.roundToTwoPlaces;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static org.junit.Assert.assertTrue;

public class CartPage extends BasePage<CartPage> {
	private static final String quantityNameAttribute = "quantity";
	private static final String deleteItem = "sc-action-delete";

	private final NavBarPageBlock navBar;

	@FindBy(css = "#gutterCartViewForm .sc-subtotal")
	private WebElement proceedToCheckoutSubTotal;

	@FindBy(id = "sc-subtotal-label-activecart")
	private WebElement itemsSubTotal;

	@FindBy(id = "sc-subtotal-amount-activecart")
	private WebElement itemsPricesSubTotal;

	@FindBy(css = "[data-name=\"Active Items\"] .sc-list-item:not([data-removed=true])")
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
		return roundToTwoPlaces(
			items
				.stream()
				.map(item -> {
					double price = getItemPrice(item);
					int quantity = getQuantityFromSelect(item.findElement(By.name(quantityNameAttribute)));
					return price * quantity;
				})
				.reduce(0.0, (prev, next) -> prev + next)
		);
	}

	public int getProceedToCheckoutSubTotalItemsCount() {
		return getItemsCountFromSubTotalString(proceedToCheckoutSubTotal.getText());
	}

	public int getItemsSubTotalItemsCount() {
		return getItemsCountFromSubTotalString(itemsSubTotal.getText());
	}

	public double getItemsSubTotalPrice() {
		return getItemsPriceFromMoneyString(itemsPricesSubTotal.getText());
	}

	public double getProceedToCheckoutItemsPrice() {
		return getItemsPriceFromMoneyString(proceedToCheckoutPrice.getText());
	}

	public void deleteItem(WebElement item) {
		WebElement delete = item.findElement(By.className(deleteItem));
		delete.click();
		waitUntilItemsCountChange(item);
	}

	@Nullable
	public WebElement getItemByTextInItemLink(String textInItemLink) {
		for (WebElement item : items) {
			boolean itemIsFound = item.findElement(By.className("sc-product-title")).getText().contains(textInItemLink);

			if (itemIsFound) {
				return item;
			}
		}

		return null;
	}

	public CartPage setItemQuantity(WebElement item, int quantity) {
		WebElement select = item.findElement(By.name(quantityNameAttribute));
		new Select(select).selectByValue(Integer.toString(quantity));
		// quantity change takes some time:
		waitUntilItemsCountChange(item);

		return this;
	}

	public double getItemPrice(WebElement item) {
		return getItemsPriceFromMoneyString(item.findElement(By.className("sc-price")).getText());
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

	private double getItemsPriceFromMoneyString(String subTotalPrice) {
		return roundToTwoPlaces(parseFloat(subTotalPrice.replaceAll("[^\\d.]+", "")));
	}

	private int getQuantityFromSelect(WebElement select) {
		return parseInt(new Select(select).getFirstSelectedOption().getText().trim());
	}

	private void waitUntilItemsCountChange(WebElement item) {
		wait.until(ExpectedConditions.invisibilityOf(item.findElement(By.className("sc-list-item-overwrap"))));
	}
}
