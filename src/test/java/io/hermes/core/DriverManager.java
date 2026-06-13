package io.hermes.core;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

/**
 * Owns the driver lifecycle: a single driver for the whole run, with a deterministic
 * app state reset between scenarios so every scenario starts from the same clean,
 * logged-out catalog and tests stay fully independent.
 *
 * <p>On Android the reset clears the app data ({@code mobile: clearApp}), which is the
 * only reliable way to guarantee a logged-out session on slower CI devices. The SUT's
 * documented long-press reset is also exercised; on iOS it is the reset mechanism.</p>
 */
public final class DriverManager {

    private static final String RESET_APP_LOGO = "longpress reset app";

    private static AppiumDriver driver;

    private DriverManager() {
    }

    public static void start() {
        if (driver == null) {
            driver = DriverFactory.createDriver();
        }
    }

    public static void stop() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static AppiumDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("Driver not started — call DriverManager.start() first");
        }
        return driver;
    }

    /** Restarts the app and resets its state so scenarios stay fully independent. */
    public static void resetAppState() {
        AppiumDriver d = getDriver();
        InteractsWithApps apps = (InteractsWithApps) d;

        apps.terminateApp(Config.APP_ID);
        if (Config.platform() == Platform.ANDROID) {
            // pm clear: wipes app data → guaranteed logged-out + empty cart, the only
            // reset that is reliable on slow CI emulators.
            d.executeScript("mobile: clearApp", Map.of("appId", Config.APP_ID));
        }
        apps.activateApp(Config.APP_ID);

        WebDriverWait wait = new WebDriverWait(d, Duration.ofSeconds(20));
        WebElement logo = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.accessibilityId(RESET_APP_LOGO)));
        // Also exercise the SUT's documented long-press reset (the reset mechanism on iOS).
        new Gestures(d).longPress(logo, Duration.ofSeconds(1));
        if (NativeDialogs.acceptIfPresent(d, Duration.ofSeconds(5))) {
            NativeDialogs.acceptIfPresent(d, Duration.ofSeconds(2));
        }
    }
}
