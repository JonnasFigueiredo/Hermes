package io.hermes.core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Handles native alert dialogs (e.g. the SUT's reset and logout confirmations)
 * on both platforms.
 */
public final class NativeDialogs {

    /** Positive button of a native Android alert. */
    private static final By ANDROID_POSITIVE_BUTTON = By.id("android:id/button1");

    private NativeDialogs() {
    }

    /**
     * Accepts a native dialog if one shows up within the timeout; returns whether one
     * was accepted. Some flows chain two dialogs (confirm + acknowledge), so callers
     * can invoke this repeatedly.
     */
    public static boolean acceptIfPresent(AppiumDriver driver, Duration timeout) {
        try {
            if (driver instanceof AndroidDriver) {
                new WebDriverWait(driver, timeout)
                        .until(ExpectedConditions.elementToBeClickable(ANDROID_POSITIVE_BUTTON))
                        .click();
            } else {
                new WebDriverWait(driver, timeout)
                        .until(ExpectedConditions.alertIsPresent())
                        .accept();
            }
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
