package io.hermes.core;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Owns the driver lifecycle: a single driver for the whole run, with a deterministic
 * app state reset between scenarios via the SUT's documented long-press on the header
 * logo, so every scenario starts from the same clean catalog screen.
 */
public final class DriverManager {

    private static final String RESET_APP_LOGO = "longpress reset app";

    private static AndroidDriver driver;

    private DriverManager() {
    }

    public static void start() {
        if (driver == null) {
            driver = DriverFactory.createAndroidDriver();
        }
    }

    public static void stop() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static AndroidDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("Driver not started — call DriverManager.start() first");
        }
        return driver;
    }

    /** Restarts the app and resets its state so scenarios stay fully independent. */
    public static void resetAppState() {
        AndroidDriver d = getDriver();
        d.terminateApp(Config.APP_PACKAGE);
        d.activateApp(Config.APP_PACKAGE);
        WebDriverWait wait = new WebDriverWait(d, Duration.ofSeconds(20));
        WebElement logo = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.accessibilityId(RESET_APP_LOGO)));
        new Gestures(d).longPress(logo, Duration.ofSeconds(1));
        // The reset asks for confirmation and may chain an acknowledge dialog;
        // without accepting them the logged-in session would leak across scenarios.
        if (NativeDialogs.acceptIfPresent(d, Duration.ofSeconds(5))) {
            NativeDialogs.acceptIfPresent(d, Duration.ofSeconds(2));
        }
    }
}
