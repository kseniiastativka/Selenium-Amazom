package com.stativka.selenium.amazon.app;

import com.stativka.selenium.amazon.listeners.ScreenshotsListener;
import com.stativka.selenium.amazon.pages.NavBarPageBlock;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * Global starting point of the application
 */
public class Application {
	public final NavBarPageBlock navBar;

	private EventFiringWebDriver driver;

	public Application() {
		driver = new EventFiringWebDriver(new ChromeDriver());
		driver.register(new ScreenshotsListener());
		navBar = new NavBarPageBlock(driver);
	}

	public void goFullScreen() {
		driver.manage().window().maximize();
	}

	public void quit() {
		driver.quit();
	}
}
