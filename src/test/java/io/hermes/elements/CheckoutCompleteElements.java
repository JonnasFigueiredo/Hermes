package io.hermes.elements;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/** Locators of the checkout complete screen. */
public final class CheckoutCompleteElements {

    public static final By SCREEN = AppiumBy.accessibilityId("checkout complete screen");
    public static final By CONTINUE_SHOPPING_BUTTON = AppiumBy.accessibilityId("Continue Shopping button");

    private CheckoutCompleteElements() {
    }
}
