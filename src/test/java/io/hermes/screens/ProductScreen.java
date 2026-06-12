package io.hermes.screens;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.hermes.screens.components.NavBar;
import org.openqa.selenium.By;

public class ProductScreen extends BaseScreen {

    private static final By PRODUCT_PRICE = AppiumBy.accessibilityId("product price");
    private static final By ADD_TO_CART_BUTTON = AppiumBy.accessibilityId("Add To Cart button");

    private final NavBar navBar;

    public ProductScreen(AndroidDriver driver) {
        super(driver);
        this.navBar = new NavBar(driver);
    }

    public NavBar navBar() {
        return navBar;
    }

    public boolean isLoaded() {
        return isVisible(PRODUCT_PRICE);
    }

    public String price() {
        return waitVisible(PRODUCT_PRICE).getText();
    }

    public boolean hasAddToCartButton() {
        return isVisible(ADD_TO_CART_BUTTON);
    }

    public void addToCart() {
        gestures.scrollIntoView(ADD_TO_CART_BUTTON);
        tap(ADD_TO_CART_BUTTON);
    }
}
