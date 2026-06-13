package io.hermes.pages;

import io.appium.java_client.AppiumDriver;
import io.hermes.elements.CheckoutPaymentElements;
import io.hermes.model.PaymentCard;

public class CheckoutPaymentPage extends BasePage {

    public CheckoutPaymentPage(AppiumDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isVisible(CheckoutPaymentElements.SCREEN, DEFAULT_TIMEOUT);
    }

    public void fillCard(PaymentCard card) {
        typeIntoForm(CheckoutPaymentElements.FULL_NAME_FIELD, card.holder());
        if (!card.number().isEmpty()) {
            typeIntoForm(CheckoutPaymentElements.CARD_NUMBER_FIELD, card.number());
        }
        typeIntoForm(CheckoutPaymentElements.EXPIRATION_DATE_FIELD, card.expirationDate());
        typeIntoForm(CheckoutPaymentElements.SECURITY_CODE_FIELD, card.securityCode());
    }

    public CheckoutReviewPage reviewOrder() {
        hideKeyboardIfPresent();
        gestures.scrollIntoView(CheckoutPaymentElements.REVIEW_ORDER_BUTTON);
        tap(CheckoutPaymentElements.REVIEW_ORDER_BUTTON);
        return new CheckoutReviewPage(driver);
    }

    public String cardNumberError() {
        return nonBlankTextOf(CheckoutPaymentElements.CARD_NUMBER_ERROR);
    }
}
