package io.hermes.elements;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/** Locators of the cart screen. */
public final class CartElements {

    public static final By SCREEN = AppiumBy.accessibilityId("cart screen");
    public static final By PRODUCT_ROW = AppiumBy.accessibilityId("product row");
    public static final By PRODUCT_LABEL = AppiumBy.accessibilityId("product label");
    public static final By REMOVE_ITEM_BUTTON = AppiumBy.accessibilityId("remove item");
    public static final By TOTAL_PRICE = AppiumBy.accessibilityId("total price");
    public static final By PROCEED_TO_CHECKOUT_BUTTON = AppiumBy.accessibilityId("Proceed To Checkout button");
    public static final By GO_SHOPPING_BUTTON = AppiumBy.accessibilityId("Go Shopping button");

    private CartElements() {
    }
}
