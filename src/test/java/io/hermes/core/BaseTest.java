package io.hermes.core;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * One driver per test class. Before each test the app is restarted and its state reset
 * via the SUT's documented long-press on the header logo, so every test starts from
 * the same clean catalog screen and tests stay fully independent.
 */
@ExtendWith(ScreenshotOnFailure.class)
public abstract class BaseTest {

    private static final String RESET_APP_LOGO = "longpress reset app";

    protected static AndroidDriver driver;

    @BeforeAll
    static void startDriver() {
        driver = DriverFactory.createAndroidDriver();
    }

    @AfterAll
    static void stopDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @BeforeEach
    void resetAppState() {
        driver.terminateApp(Config.APP_PACKAGE);
        driver.activateApp(Config.APP_PACKAGE);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement logo = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.accessibilityId(RESET_APP_LOGO)));
        new Gestures(driver).longPress(logo, Duration.ofSeconds(1));
    }

    public static AndroidDriver getDriver() {
        return driver;
    }
}
