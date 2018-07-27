package com.stativka.selenium.amazon.pages;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage<T extends BasePage<T>> extends LoadableComponent<T> {
    private static final int defaultWaitTimeout = 10;
    protected final WebDriverWait wait;
    final WebDriver driver;

    public BasePage(@NotNull WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, defaultWaitTimeout);
        PageFactory.initElements(driver, this);
    }

    protected void goFullScreen() {
        driver.manage().window().maximize();
    }

    public void clearCookiesAndRefresh() {
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
    }
}
