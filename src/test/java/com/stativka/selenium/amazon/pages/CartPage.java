package com.stativka.selenium.amazon.pages;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class CartPage extends BasePage<CartPage> {

    private final MainPage mainPage;

    @FindBy(css = "#gutterCartViewForm .sc-subtotal")
    private WebElement proceedToCheckoutSubTotal;

    @FindBy(id = "sc-subtotal-label-activecart")
    private WebElement itemsSubTotal;

    @FindBy(name = "quantity")
    private List<WebElement> quantitySelects;

    CartPage(@NotNull WebDriver driver, MainPage mainPage) {
        super(driver);
        this.mainPage = mainPage;
    }

    public void checkDom() {
        System.out.println(proceedToCheckoutSubTotal.getText());
        System.out.println(itemsSubTotal.getText());

        for (WebElement select : quantitySelects) {
            System.out.println(select);
            System.out.println(new Select(select).getFirstSelectedOption().getText());
        }
    }

    public int getProceedToCheckoutSubTotalItemsCount() {
        return getItemsCountFromSubTotalString(proceedToCheckoutSubTotal.getText());
    }

    public int getItemsSubTotalItemsCount() {
        return getItemsCountFromSubTotalString(itemsSubTotal.getText());
    }

    @Override
    protected void load() {
        mainPage.goToCart();
    }

    @Override
    protected void isLoaded() throws Error {
        assertTrue("Cart is not loaded", driver.getCurrentUrl().contains("/cart/"));
    }

    private int getItemsCountFromSubTotalString(String subTotal) {
        return Character.getNumericValue(subTotal.charAt(subTotal.indexOf('(') + 1));
    }
}
