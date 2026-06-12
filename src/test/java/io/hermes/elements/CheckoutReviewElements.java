package io.hermes.elements;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/** Locators of the checkout order review screen. */
public final class CheckoutReviewElements {

    public static final By SCREEN = AppiumBy.accessibilityId("checkout review order screen");
    public static final By PLACE_ORDER_BUTTON = AppiumBy.accessibilityId("Place Order button");

    private CheckoutReviewElements() {
    }
}
