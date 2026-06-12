package io.hermes.pages;

import io.appium.java_client.android.AndroidDriver;
import io.hermes.elements.CheckoutReviewElements;

public class CheckoutReviewPage extends BasePage {

    public CheckoutReviewPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isVisible(CheckoutReviewElements.SCREEN);
    }

    public CheckoutCompletePage placeOrder() {
        gestures.scrollIntoView(CheckoutReviewElements.PLACE_ORDER_BUTTON);
        tap(CheckoutReviewElements.PLACE_ORDER_BUTTON);
        return new CheckoutCompletePage(driver);
    }
}
