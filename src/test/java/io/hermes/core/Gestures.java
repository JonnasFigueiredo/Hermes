package io.hermes.core;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.time.Duration;
import java.util.Map;

/**
 * Native gestures implemented with modern {@code mobile:} commands (no legacy TouchAction).
 */
public final class Gestures {

    private static final int MAX_SCROLLS = 5;

    private final AndroidDriver driver;

    public Gestures(AndroidDriver driver) {
        this.driver = driver;
    }

    public void longPress(WebElement element, Duration duration) {
        driver.executeScript("mobile: longClickGesture", Map.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "duration", duration.toMillis()));
    }

    /** Scrolls down over the middle of the screen. */
    public void scrollDown() {
        Dimension size = driver.manage().window().getSize();
        driver.executeScript("mobile: scrollGesture", Map.of(
                "left", size.getWidth() / 4,
                "top", size.getHeight() / 4,
                "width", size.getWidth() / 2,
                "height", size.getHeight() / 2,
                "direction", "down",
                "percent", 0.8));
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
