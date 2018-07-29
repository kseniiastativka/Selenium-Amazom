package com.stativka.selenium.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage<T extends BasePage<T>> extends LoadableComponent<T> {
	private static final int defaultWaitTimeout = 10;
	protected final WebDriverWait wait;
	final WebDriver driver;

	BasePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, defaultWaitTimeout);
		PageFactory.initElements(driver, this);
	}

	@Override
	protected void load() {
		// leave empty since not all pages are accessible by url or navbar link
	}

	public void clearCookiesAndRefresh() {
		driver.manage().deleteAllCookies();
		driver.navigate().refresh();
	}

	String getUrl() {
		return driver.getCurrentUrl();
	}
}
