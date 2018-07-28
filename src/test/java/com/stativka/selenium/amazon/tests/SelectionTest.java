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
		app.mainPage.clearCookiesAndRefresh();
	}

	@Test
	@UseDataProvider(value = "validSearchItems", location = DataProviders.class)
	public void testSearchAndAdditionOfItemsToCart(List<SearchItem> searchItems) {
		int quantityOfSearchItems = 0;

		for (SearchItem searchItem : searchItems) {
			addSearchItemToCart(searchItem);

			int cartItemsCount = app.mainPage.getCartItemsCount();

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

		CartPage cartPage = app.mainPage
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

	private void addSearchItemToCart(SearchItem searchItem) {
		ItemPage itemPage = app.mainPage
			.get()
			.search(searchItem.searchTerm)
			.goToItemByText(searchItem.textInItemLink);

		if (itemPage == null) {
			fail("Cannot find " + searchItem.searchTerm + " with " + searchItem.textInItemLink);
		}

		itemPage.addToCart(searchItem.getQuantity());
	}
}
