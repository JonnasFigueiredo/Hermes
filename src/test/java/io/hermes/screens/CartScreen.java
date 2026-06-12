package io.hermes.screens;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.hermes.screens.components.NavBar;
import org.openqa.selenium.By;

public class CartScreen extends BaseScreen {

    private static final By PRODUCT_ROW = AppiumBy.accessibilityId("product row");
    private static final By REMOVE_ITEM_BUTTON = AppiumBy.accessibilityId("remove item");
    private static final By PROCEED_TO_CHECKOUT_BUTTON = AppiumBy.accessibilityId("Proceed To Checkout button");
    private static final By GO_SHOPPING_BUTTON = AppiumBy.accessibilityId("Go Shopping button");

    private final NavBar navBar;

    public CartScreen(AndroidDriver driver) {
        super(driver);
        this.navBar = new NavBar(driver);
    }

    public NavBar navBar() {
        return navBar;
    }

    public boolean isLoaded() {
        return isVisible(PROCEED_TO_CHECKOUT_BUTTON) || isEmpty();
    }

    public int itemCount() {
        return waitAllVisible(PRODUCT_ROW).size();
    }

    public void removeFirstItem() {
        gestures.scrollIntoView(REMOVE_ITEM_BUTTON);
        tap(REMOVE_ITEM_BUTTON);
    }

    /** The cart is empty when the SUT shows its "Go Shopping" call to action. */
    public boolean isEmpty() {
        return isVisible(GO_SHOPPING_BUTTON);
    }
}
