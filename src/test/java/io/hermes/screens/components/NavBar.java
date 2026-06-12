package io.hermes.screens.components;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.hermes.screens.BaseScreen;
import org.openqa.selenium.By;

/**
 * Header navigation bar, shared by every screen via composition.
 */
public class NavBar extends BaseScreen {

    private static final By OPEN_MENU_BUTTON = AppiumBy.accessibilityId("open menu");
    private static final By CART_BADGE = AppiumBy.accessibilityId("cart badge");
    private static final By MENU_ITEM_LOG_IN = AppiumBy.accessibilityId("menu item log in");
    private static final By MENU_ITEM_CATALOG = AppiumBy.accessibilityId("menu item catalog");

    public NavBar(AndroidDriver driver) {
        super(driver);
    }

    public void openLoginFromMenu() {
        tap(OPEN_MENU_BUTTON);
        tap(MENU_ITEM_LOG_IN);
    }

    public void openCatalogFromMenu() {
        tap(OPEN_MENU_BUTTON);
        tap(MENU_ITEM_CATALOG);
    }

    public void openCart() {
        tap(CART_BADGE);
    }

    /** Text shown on the cart badge, e.g. "1" when one item is in the cart. */
    public String cartBadgeText() {
        return nonBlankTextOf(CART_BADGE);
    }
}
