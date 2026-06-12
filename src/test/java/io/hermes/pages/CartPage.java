package io.hermes.pages;

import io.appium.java_client.android.AndroidDriver;
import io.hermes.elements.CartElements;
import io.hermes.pages.components.NavBar;

public class CartPage extends BasePage {

    private final NavBar navBar;

    public CartPage(AndroidDriver driver) {
        super(driver);
        this.navBar = new NavBar(driver);
    }

    public NavBar navBar() {
        return navBar;
    }

    public boolean isLoaded() {
        return isVisible(CartElements.SCREEN);
    }

    public int itemCount() {
        return waitAllVisible(CartElements.PRODUCT_ROW).size();
    }

    public String totalPrice() {
        return nonBlankTextOf(CartElements.TOTAL_PRICE);
    }

    public void removeFirstItem() {
        gestures.scrollIntoView(CartElements.REMOVE_ITEM_BUTTON);
        tap(CartElements.REMOVE_ITEM_BUTTON);
    }

    /** The cart is empty when the SUT shows its "Go Shopping" call to action. */
    public boolean isEmpty() {
        return isVisible(CartElements.GO_SHOPPING_BUTTON);
    }

    public CatalogPage goShopping() {
        tap(CartElements.GO_SHOPPING_BUTTON);
        return new CatalogPage(driver);
    }

    public void proceedToCheckout() {
        gestures.scrollIntoView(CartElements.PROCEED_TO_CHECKOUT_BUTTON);
        tap(CartElements.PROCEED_TO_CHECKOUT_BUTTON);
    }
}
