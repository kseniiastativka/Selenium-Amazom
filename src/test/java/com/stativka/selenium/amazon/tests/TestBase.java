package com.stativka.selenium.amazon.tests;

import com.stativka.selenium.amazon.app.Application;
import org.junit.Before;

public abstract class TestBase {
	private static ThreadLocal<Application> tlApp = new ThreadLocal<>();

	Application app;

	@Before
	public void start() {
		if (tlApp.get() != null) {
			app = tlApp.get(); // use app from cache
			return;
		}

		app = new Application(); // create new instance of the app if not cached yet

		tlApp.set(app);

		app.goFullScreen();

		app.navBar.get();

		// tear down on shutdown
		Runtime.getRuntime().addShutdownHook(
			new Thread(() -> {
				app.quit();
				app = null;
			})
		);
	}
}
