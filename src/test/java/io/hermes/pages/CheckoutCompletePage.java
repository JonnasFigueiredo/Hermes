package io.hermes.pages;

import io.appium.java_client.android.AndroidDriver;
import io.hermes.elements.CheckoutCompleteElements;

public class CheckoutCompletePage extends BasePage {

    public CheckoutCompletePage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isVisible(CheckoutCompleteElements.SCREEN);
    }

    public CatalogPage continueShopping() {
        gestures.scrollIntoView(CheckoutCompleteElements.CONTINUE_SHOPPING_BUTTON);
        tap(CheckoutCompleteElements.CONTINUE_SHOPPING_BUTTON);
        return new CatalogPage(driver);
    }
}
