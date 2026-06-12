package io.hermes.elements;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/** Locators of the product catalog screen and its sort modal. */
public final class CatalogElements {

    public static final By SCREEN = AppiumBy.accessibilityId("products screen");
    public static final By STORE_ITEM = AppiumBy.accessibilityId("store item");
    public static final By STORE_ITEM_TEXT = AppiumBy.accessibilityId("store item text");
    public static final By STORE_ITEM_PRICE = AppiumBy.accessibilityId("store item price");

    public static final By SORT_BUTTON = AppiumBy.accessibilityId("sort button");
    public static final By SORT_NAME_ASCENDING = AppiumBy.accessibilityId("nameAsc");
    public static final By SORT_NAME_DESCENDING = AppiumBy.accessibilityId("nameDesc");
    public static final By SORT_PRICE_ASCENDING = AppiumBy.accessibilityId("priceAsc");
    public static final By SORT_PRICE_DESCENDING = AppiumBy.accessibilityId("priceDesc");

    private CatalogElements() {
    }
}
