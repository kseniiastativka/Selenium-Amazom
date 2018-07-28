package com.stativka.selenium.amazon.tests;

import com.stativka.selenium.amazon.models.SearchItem;
import com.stativka.selenium.amazon.pages.CartPage;
import com.stativka.selenium.amazon.pages.ItemPage;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class SelectionTest extends TestBase {
    private static final SearchItem[] searchItems = {
            new SearchItem("MacBook Pro 15", "16GB"),
            new SearchItem("Surface Pro", "8GB"),
            new SearchItem("Google Pixelbook", "8GB"),
    };

    @After
    public void tearDown() {
        app.mainPage.clearCookiesAndRefresh();
    }

    @Test
    @DisplayName("Cart icon should display correct number of added items")
    public void testSearchAndAdditionOfItemsToCart() {
        int counter = 1;

        for (SearchItem searchItem : searchItems) {
            addSearchItemToCart(searchItem);

            int cartItemsCount = app.mainPage.getCartItemsCount();

            assertEquals("Wrong number of items in the cart", counter, cartItemsCount);

            counter++;
        }


        int cartItemsCount = app.mainPage.getCartItemsCount();

        assertEquals("Wrong number of items in the cart", searchItems.length, cartItemsCount);
    }

    @Test
    @DisplayName("Total quantity of items should be correctly displayed on the Cart page")
    public void testItemsQuantityInCart() {
        for (SearchItem searchItem : searchItems) {
            addSearchItemToCart(searchItem);
        }

        CartPage cartPage = app.mainPage
                .get()
                .goToCart();

        int itemsSubTotalItemsCount = cartPage.getItemsSubTotalItemsCount();
        int proceedToCheckoutSubTotalItemsCount = cartPage.getProceedToCheckoutSubTotalItemsCount();
        int itemsCountOnCartMenuIcon = app.mainPage.getCartItemsCount();

        assertEquals(
                "Items quantity count in the Proceed to checkout form is not synced with a count under " +
                        "items list",
                proceedToCheckoutSubTotalItemsCount,
                itemsSubTotalItemsCount
        );

        assertEquals(
                "Items quantity count on the Cart menu icon is not synced with a count under items list",
                itemsCountOnCartMenuIcon,
                itemsSubTotalItemsCount
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

        itemPage.addToCart();
    }
}
