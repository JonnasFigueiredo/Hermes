package io.hermes.elements;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/** Locators of the header bar and drawer menu, present on every screen. */
public final class NavBarElements {

    public static final By OPEN_MENU_BUTTON = AppiumBy.accessibilityId("open menu");
    public static final By CART_BADGE = AppiumBy.accessibilityId("cart badge");
    public static final By RESET_APP_LOGO = AppiumBy.accessibilityId("longpress reset app");

    public static final By MENU_ITEM_LOG_IN = AppiumBy.accessibilityId("menu item log in");
    public static final By MENU_ITEM_LOG_OUT = AppiumBy.accessibilityId("menu item log out");
    public static final By MENU_ITEM_CATALOG = AppiumBy.accessibilityId("menu item catalog");

    private NavBarElements() {
    }
}
