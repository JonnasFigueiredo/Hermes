package io.hermes.core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

/**
 * Native gestures implemented with modern {@code mobile:} commands (no legacy
 * TouchAction), dispatching to the platform-specific command set.
 */
public final class Gestures {

    private static final int MAX_SCROLLS = 5;

    private final AppiumDriver driver;
    private final boolean android;

    public Gestures(AppiumDriver driver) {
        this.driver = driver;
        this.android = driver instanceof AndroidDriver;
    }

    public void longPress(WebElement element, Duration duration) {
        String elementId = ((RemoteWebElement) element).getId();
        if (android) {
            driver.executeScript("mobile: longClickGesture", Map.of(
                    "elementId", elementId,
                    "duration", duration.toMillis()));
        } else {
            driver.executeScript("mobile: touchAndHold", Map.of(
                    "elementId", elementId,
                    "duration", duration.toMillis() / 1000.0));
        }
    }

    /** Scrolls down over the middle of the screen. */
    public void scrollDown() {
        if (android) {
            Dimension size = driver.manage().window().getSize();
            driver.executeScript("mobile: scrollGesture", Map.of(
                    "left", size.getWidth() / 4,
                    "top", size.getHeight() / 4,
                    "width", size.getWidth() / 2,
                    "height", size.getHeight() / 2,
                    "direction", "down",
                    "percent", 0.8));
        } else {
            // On iOS, swiping up moves the content down one "page".
            driver.executeScript("mobile: swipe", Map.of("direction", "up"));
        }
    }

    /**
     * Scrolls down until the locator is present, waiting briefly at each step so a
     * screen that is still rendering gets a chance before we scroll past it. Throws
     * if the element never appears.
     */
    public WebElement scrollIntoView(By locator) {
        for (int i = 0; i <= MAX_SCROLLS; i++) {
            try {
                return new WebDriverWait(driver, Duration.ofSeconds(2))
                        .until(ExpectedConditions.presenceOfElementLocated(locator));
            } catch (TimeoutException notYet) {
                if (i < MAX_SCROLLS) {
                    scrollDown();
                }
            }
        }
        throw new org.openqa.selenium.NoSuchElementException(
                "Element not found after scrolling: " + locator);
    }
}
