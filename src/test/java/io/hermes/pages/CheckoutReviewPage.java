package io.hermes.pages;

import io.appium.java_client.AppiumDriver;
import io.hermes.elements.CheckoutReviewElements;

public class CheckoutReviewPage extends BasePage {

    public CheckoutReviewPage(AppiumDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isVisible(CheckoutReviewElements.SCREEN, DEFAULT_TIMEOUT);
    }

    public CheckoutCompletePage placeOrder() {
        gestures.scrollIntoView(CheckoutReviewElements.PLACE_ORDER_BUTTON);
        tap(CheckoutReviewElements.PLACE_ORDER_BUTTON);
        return new CheckoutCompletePage(driver);
    }
}
