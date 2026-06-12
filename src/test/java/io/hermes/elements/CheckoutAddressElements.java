package io.hermes.elements;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/** Locators of the checkout shipping address screen. */
public final class CheckoutAddressElements {

    public static final By SCREEN = AppiumBy.accessibilityId("checkout address screen");
    public static final By FULL_NAME_FIELD = AppiumBy.accessibilityId("Full Name* input field");
    public static final By ADDRESS_LINE_1_FIELD = AppiumBy.accessibilityId("Address Line 1* input field");
    public static final By CITY_FIELD = AppiumBy.accessibilityId("City* input field");
    public static final By ZIP_CODE_FIELD = AppiumBy.accessibilityId("Zip Code* input field");
    public static final By COUNTRY_FIELD = AppiumBy.accessibilityId("Country* input field");
    public static final By TO_PAYMENT_BUTTON = AppiumBy.accessibilityId("To Payment button");

    private CheckoutAddressElements() {
    }
}
