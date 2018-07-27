package com.stativka.selenium.amazon.listeners;

import com.google.common.io.Files;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import java.io.File;
import java.io.IOException;

public class ScreenshotsListener extends AbstractWebDriverEventListener {
    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        File tempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File screenshotFile = new File("screenshot-" + System.currentTimeMillis() + ".png");
        try {
            Files.copy(tempFile, screenshotFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
