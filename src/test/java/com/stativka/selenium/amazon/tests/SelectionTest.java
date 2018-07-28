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
        int counter = 1;

        for (SearchItem searchItem : searchItems) {
            addSearchItemToCart(searchItem);

            int cartItemsCount = app.mainPage.getCartItemsCount();

            assertEquals("Wrong number of items in the cart", counter, cartItemsCount);

            counter++;
        }


        int cartItemsCount = app.mainPage.getCartItemsCount();

        assertEquals("Wrong number of items in the cart", searchItems.size(), cartItemsCount);
    }

    @Test
    @UseDataProvider(value = "validSearchItems", location = DataProviders.class)
    public void testItemsQuantityInCart(List<SearchItem> searchItems) {
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
