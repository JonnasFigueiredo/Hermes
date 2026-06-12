package io.hermes.core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

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

    /** Scrolls down until the locator matches at least one element, or gives up. */
    public WebElement scrollIntoView(By locator) {
        for (int i = 0; i < MAX_SCROLLS; i++) {
            var found = driver.findElements(locator);
            if (!found.isEmpty()) {
                return found.get(0);
            }
            scrollDown();
        }
        return driver.findElement(locator);
    }
}
