package io.hermes.elements;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/** Locators of the product details screen. */
public final class ProductElements {

    public static final By SCREEN = AppiumBy.accessibilityId("product screen");
    public static final By PRICE = AppiumBy.accessibilityId("product price");
    public static final By ADD_TO_CART_BUTTON = AppiumBy.accessibilityId("Add To Cart button");

    public static final By COUNTER_AMOUNT = AppiumBy.accessibilityId("counter amount");
    public static final By COUNTER_PLUS_BUTTON = AppiumBy.accessibilityId("counter plus button");
    public static final By COUNTER_MINUS_BUTTON = AppiumBy.accessibilityId("counter minus button");

    private ProductElements() {
    }
}
