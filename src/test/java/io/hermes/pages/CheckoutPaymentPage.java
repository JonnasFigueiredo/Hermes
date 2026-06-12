package io.hermes.pages;

import io.appium.java_client.AppiumDriver;
import io.hermes.elements.CheckoutPaymentElements;
import io.hermes.model.PaymentCard;

public class CheckoutPaymentPage extends BasePage {

    public CheckoutPaymentPage(AppiumDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isVisible(CheckoutPaymentElements.SCREEN);
    }

    public void fillCard(PaymentCard card) {
        type(CheckoutPaymentElements.FULL_NAME_FIELD, card.holder());
        type(CheckoutPaymentElements.CARD_NUMBER_FIELD, card.number());
        type(CheckoutPaymentElements.EXPIRATION_DATE_FIELD, card.expirationDate());
        type(CheckoutPaymentElements.SECURITY_CODE_FIELD, card.securityCode());
    }

    public CheckoutReviewPage reviewOrder() {
        gestures.scrollIntoView(CheckoutPaymentElements.REVIEW_ORDER_BUTTON);
        tap(CheckoutPaymentElements.REVIEW_ORDER_BUTTON);
        return new CheckoutReviewPage(driver);
    }

    public String cardNumberError() {
        return nonBlankTextOf(CheckoutPaymentElements.CARD_NUMBER_ERROR);
    }
}
