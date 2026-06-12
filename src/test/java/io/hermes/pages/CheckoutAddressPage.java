package io.hermes.pages;

import io.appium.java_client.android.AndroidDriver;
import io.hermes.elements.CheckoutAddressElements;
import io.hermes.model.Address;

public class CheckoutAddressPage extends BasePage {

    public CheckoutAddressPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isVisible(CheckoutAddressElements.SCREEN);
    }

    public CheckoutPaymentPage fillAndGoToPayment(Address address) {
        type(CheckoutAddressElements.FULL_NAME_FIELD, address.fullName());
        type(CheckoutAddressElements.ADDRESS_LINE_1_FIELD, address.addressLine1());
        type(CheckoutAddressElements.CITY_FIELD, address.city());
        type(CheckoutAddressElements.ZIP_CODE_FIELD, address.zipCode());
        type(CheckoutAddressElements.COUNTRY_FIELD, address.country());
        gestures.scrollIntoView(CheckoutAddressElements.TO_PAYMENT_BUTTON);
        tap(CheckoutAddressElements.TO_PAYMENT_BUTTON);
        return new CheckoutPaymentPage(driver);
    }
}
