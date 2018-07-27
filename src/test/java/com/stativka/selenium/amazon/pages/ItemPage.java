package com.stativka.selenium.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.Assert.assertTrue;

public class ItemPage extends BasePage<ItemPage> {
    private static final String addToCartButtonCssSelector = "input[value=\"Add to Cart\"]";

    private final MainPage mainPage;

    @FindBy(css = addToCartButtonCssSelector)
    private WebElement addToCartButton;


    ItemPage(WebDriver driver, MainPage mainPage) {
        super(driver);
        this.mainPage = mainPage;
    }

    public void addToCart() {
        addToCartButton.click();
    }

    @Override
    protected void load() {
        mainPage.get();
    }

    @Override
    protected void isLoaded() throws Error {
        assertTrue(
                "Item page is not loaded since Add to Cart button is not present",
                addToCartButton.isDisplayed()
        );
    }
}
