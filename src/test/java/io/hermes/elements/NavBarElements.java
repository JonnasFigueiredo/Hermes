package io.hermes.elements;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * Locators of the navigation chrome. Android uses a header bar with a hamburger drawer;
 * iOS uses a bottom tab bar. The drawer menu items themselves share the same ids on
 * both platforms.
 */
public final class NavBarElements {

    public static final By OPEN_MENU_BUTTON = PlatformBy.of(
            AppiumBy.accessibilityId("open menu"),
            AppiumBy.accessibilityId("tab bar option menu"));
    // The same element opens the cart and carries the item count as its text/label:
    // Android "cart badge", iOS the "tab bar option cart" button.
    public static final By CART = PlatformBy.of(
            AppiumBy.accessibilityId("cart badge"),
            AppiumBy.accessibilityId("tab bar option cart"));
    public static final By RESET_APP_LOGO = AppiumBy.accessibilityId("longpress reset app");

    public static final By MENU_ITEM_LOG_IN = AppiumBy.accessibilityId("menu item log in");
    public static final By MENU_ITEM_LOG_OUT = AppiumBy.accessibilityId("menu item log out");
    public static final By MENU_ITEM_CATALOG = PlatformBy.of(
            AppiumBy.accessibilityId("menu item catalog"),
            AppiumBy.accessibilityId("tab bar option catalog"));

    private NavBarElements() {
    }
}
