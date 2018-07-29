package com.stativka.selenium.amazon.tests;

import com.stativka.selenium.amazon.models.SearchItem;
import com.stativka.selenium.amazon.pages.CartPage;
import com.stativka.selenium.amazon.pages.ItemPage;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

@RunWith(DataProviderRunner.class)
public class SelectionTest extends TestBase {

	@After
	public void tearDown() {
		app.navBar.clearCookiesAndRefresh();
	}

	@Test
	@UseDataProvider(value = "validSearchItems", location = DataProviders.class)
	public void testSearchAndAdditionOfItemsToCart(List<SearchItem> searchItems) {
		int quantityOfSearchItems = 0;

		for (SearchItem searchItem : searchItems) {
			addSearchItemToCart(searchItem);

			int cartItemsCount = app.navBar.getCartItemsCount();

			quantityOfSearchItems += searchItem.getQuantity();

			assertEquals("Wrong number of items in the cart", quantityOfSearchItems, cartItemsCount);
		}
	}

	@Test
	@UseDataProvider(value = "validSearchItems", location = DataProviders.class)
	public void testItemsQuantityInCart(List<SearchItem> searchItems) {
		int quantityOfSearchItems = 0;

		for (SearchItem searchItem : searchItems) {
			addSearchItemToCart(searchItem);

			quantityOfSearchItems += searchItem.getQuantity();
		}

		CartPage cartPage = app.navBar
			.get()
			.goToCart();

		assertEquals(
			"Items quantity count in the Proceed to checkout form is not correct",
			quantityOfSearchItems,
			cartPage.getProceedToCheckoutSubTotalItemsCount()
		);

		assertEquals(
			"Items quantity count under items list is not correct",
			quantityOfSearchItems,
			cartPage.getItemsSubTotalItemsCount()
		);

		assertEquals(
			"Quantity selectors sum is not correct",
			quantityOfSearchItems,
			cartPage.getQuantitySelectorsItemsCount()
		);
	}

	@Test
	@UseDataProvider(value = "validSearchItems", location = DataProviders.class)
	public void testItemsQuantityIncreaseInCart(List<SearchItem> searchItems) {
		int quantityOfSearchItems = 0;

		for (SearchItem searchItem : searchItems) {
			addSearchItemToCart(searchItem);

			quantityOfSearchItems += searchItem.getQuantity();
		}
		int increaseAmount = 1;
		int quantityOfSearchItemsAfterIncrease = quantityOfSearchItems + increaseAmount;
			SearchItem firstSearchItem = searchItems.get(0);

		CartPage cartPage = app.navBar
			.get()
			.goToCart()
			.setItemQuantity(firstSearchItem.textInItemLink, firstSearchItem.getQuantity() + increaseAmount);

		assertEquals(
			"Items quantity in the Proceed to checkout form is not correct",
			quantityOfSearchItemsAfterIncrease,
			cartPage.getProceedToCheckoutSubTotalItemsCount()
		);

		assertEquals(
			"Items quantity count under items list is not correct",
			quantityOfSearchItemsAfterIncrease,
			cartPage.getItemsSubTotalItemsCount()
		);

		assertEquals(
			"Quantity selectors sum is not correct",
			quantityOfSearchItemsAfterIncrease,
			cartPage.getQuantitySelectorsItemsCount()
		);
	}

	@Test
	@UseDataProvider(value = "validSearchItems", location = DataProviders.class)
	public void testItemsPriceInCard(List<SearchItem> searchItems) {
		for (SearchItem searchItem : searchItems) {
			addSearchItemToCart(searchItem);
		}

		CartPage cartPage = app.navBar
			.get()
			.goToCart();


		assertEquals(
			"Items price sum is not synced with total price under items",
			cartPage.getItemsPriceSum(),
			cartPage.getItemsSubTotalPrice()
		);

		assertEquals(
			"Items price sum is not synced with the Proceed to checkout form",
			cartPage.getItemsPriceSum(),
			cartPage.getProceedToCheckoutItemsPrice()
		);
	}

	private void addSearchItemToCart(SearchItem searchItem) {
		ItemPage itemPage = app.navBar
			.get()
			.search(searchItem.searchTerm)
			.goToItemByText(searchItem.textInItemLink);

		if (itemPage == null) {
			fail("Cannot find " + searchItem.searchTerm + " with " + searchItem.textInItemLink);
		}

		itemPage.addToCart(searchItem.getQuantity());
	}
}
