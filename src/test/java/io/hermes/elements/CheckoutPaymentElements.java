package io.hermes.elements;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/** Locators of the checkout payment screen. */
public final class CheckoutPaymentElements {

    public static final By SCREEN = AppiumBy.accessibilityId("checkout payment screen");
    public static final By FULL_NAME_FIELD = AppiumBy.accessibilityId("Full Name* input field");
    public static final By CARD_NUMBER_FIELD = AppiumBy.accessibilityId("Card Number* input field");
    public static final By CARD_NUMBER_ERROR = AppiumBy.accessibilityId("Card Number*-error-message");
    public static final By EXPIRATION_DATE_FIELD = AppiumBy.accessibilityId("Expiration Date* input field");
    public static final By SECURITY_CODE_FIELD = AppiumBy.accessibilityId("Security Code* input field");
    public static final By REVIEW_ORDER_BUTTON = AppiumBy.accessibilityId("Review Order button");

    private CheckoutPaymentElements() {
    }
}
