package io.hermes.pages;

import io.appium.java_client.AppiumDriver;
import io.hermes.core.Gestures;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Base for all pages and components. Synchronization is done exclusively with
 * explicit waits — no Thread.sleep anywhere.
 */
public abstract class BasePage {

    protected static final Duration DEFAULT_TIMEOUT = io.hermes.core.Config.defaultTimeout();
    protected static final Duration SHORT_TIMEOUT = io.hermes.core.Config.shortTimeout();

    protected final AppiumDriver driver;
    protected final WebDriverWait wait;
    protected final Gestures gestures;

    protected BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        this.gestures = new Gestures(driver);
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected List<WebElement> waitAllVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * All elements present in the tree, visible or not. Use for counting/indexing lists
     * whose items scroll off-screen (iOS does not report off-screen items as visible).
     */
    protected List<WebElement> waitAllPresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    /** Dismisses the soft keyboard if it is up; harmless when it is not. */
    protected void hideKeyboardIfPresent() {
        // On the iOS simulator hideKeyboard() is unreliable; tapping the keyboard's
        // Return key dismisses it (this app's fields do not auto-submit on return).
        var returnKey = driver.findElements(io.appium.java_client.AppiumBy.accessibilityId("Return"));
        if (!returnKey.isEmpty()) {
            returnKey.get(0).click();
            return;
        }
        try {
            if (driver instanceof io.appium.java_client.HidesKeyboard hidesKeyboard) {
                hidesKeyboard.hideKeyboard();
            }
        } catch (RuntimeException keyboardAlreadyHidden) {
            // no keyboard on screen — nothing to do
        }
    }

    protected boolean isVisible(By locator) {
        return isVisible(locator, SHORT_TIMEOUT);
    }

    protected boolean isVisible(By locator, Duration timeout) {
        try {
            new WebDriverWait(driver, timeout)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void tap(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        WebElement field = waitVisible(locator);
        field.clear();
        field.sendKeys(text);
    }

    /**
     * Types into a field that may sit below the fold or under the keyboard, as happens
     * on long iOS forms: dismiss the previous keyboard, scroll the field into view,
     * focus it and type. On Android, where the fields are already visible, this is
     * equivalent to {@link #type}.
     */
    protected void typeIntoForm(By locator, String text) {
        hideKeyboardIfPresent();
        WebElement field = gestures.scrollUntilVisible(locator);
        field.click();
        field.clear();
        field.sendKeys(text);
    }

    /** Visible texts of every element matched by the locator, in screen order. */
    protected List<String> textsOf(By locator) {
        return waitAllVisible(locator).stream()
                .map(WebElement::getText)
                .toList();
    }

    /**
     * Waits until the element's text equals the expected value; returns whether it did.
     * Use for texts that transition through intermediate values (e.g. the cart badge
     * going from "1" to "2"), where reading the first non-blank text would race.
     */
    protected boolean waitTextEquals(By locator, String expected) {
        try {
            wait.until(d -> {
                List<WebElement> elements = d.findElements(locator);
                return !elements.isEmpty() && expected.equals(readText(elements.get(0)));
            });
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Waits until the element has a non-blank text and returns it. React Native exposes
     * the accessibility id on a container ViewGroup whose own text is empty, so when
     * needed the text is read from the child TextViews instead.
     */
    protected String nonBlankTextOf(By locator) {
        return wait.until(d -> {
            List<WebElement> elements = d.findElements(locator);
            if (elements.isEmpty()) {
                return null;
            }
            String text = readText(elements.get(0));
            return text.isBlank() ? null : text;
        });
    }

    private static String readText(WebElement element) {
        String own = element.getText();
        if (own != null && !own.isBlank()) {
            return own;
        }
        return element.findElements(By.className("android.widget.TextView")).stream()
                .map(WebElement::getText)
                .filter(t -> t != null && !t.isBlank())
                .collect(Collectors.joining(" "));
    }
}
