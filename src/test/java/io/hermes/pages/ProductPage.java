package io.hermes.pages;

import io.appium.java_client.android.AndroidDriver;
import io.hermes.elements.ProductElements;
import io.hermes.pages.components.NavBar;

public class ProductPage extends BasePage {

    private final NavBar navBar;

    public ProductPage(AndroidDriver driver) {
        super(driver);
        this.navBar = new NavBar(driver);
    }

    public NavBar navBar() {
        return navBar;
    }

    public boolean isLoaded() {
        return isVisible(ProductElements.PRICE);
    }

    public String price() {
        return nonBlankTextOf(ProductElements.PRICE);
    }

    public boolean hasAddToCartButton() {
        return isVisible(ProductElements.ADD_TO_CART_BUTTON);
    }

    public String counterAmount() {
        return nonBlankTextOf(ProductElements.COUNTER_AMOUNT);
    }

    public void increaseQuantity() {
        tap(ProductElements.COUNTER_PLUS_BUTTON);
    }

    public void decreaseQuantity() {
        tap(ProductElements.COUNTER_MINUS_BUTTON);
    }

    /** Sets the desired quantity using the +/- counter, then adds to cart. */
    public void addToCartWithQuantity(int quantity) {
        int current = Integer.parseInt(counterAmount());
        while (current < quantity) {
            increaseQuantity();
            current++;
        }
        while (current > quantity) {
            decreaseQuantity();
            current--;
        }
        addToCart();
    }

    public void addToCart() {
        gestures.scrollIntoView(ProductElements.ADD_TO_CART_BUTTON);
        tap(ProductElements.ADD_TO_CART_BUTTON);
    }
}
