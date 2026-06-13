package io.hermes.model;

/**
 * Payment card used on the checkout flow. The valid number is the one the SUT
 * itself suggests on the payment screen placeholder.
 */
public record PaymentCard(String holder, String number, String expirationDate, String securityCode) {

    public static PaymentCard valid() {
        // Standard Luhn-valid test Visa: the app's own placeholder fails iOS validation.
        return new PaymentCard("Bob Builder", "4111111111111111", "0330", "123");
    }

    /** Card with an empty number — the SUT rejects it as a required field. */
    public static PaymentCard withoutNumber() {
        return new PaymentCard("Bob Builder", "", "0330", "123");
    }
}
