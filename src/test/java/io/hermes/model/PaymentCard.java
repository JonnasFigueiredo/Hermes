package io.hermes.model;

/**
 * Payment card used on the checkout flow. The valid number is the one the SUT
 * itself suggests on the payment screen placeholder.
 */
public record PaymentCard(String holder, String number, String expirationDate, String securityCode) {

    public static PaymentCard valid() {
        return new PaymentCard("Bob Builder", "325812657568789", "0330", "123");
    }

    public static PaymentCard withInvalidNumber() {
        return new PaymentCard("Bob Builder", "1234", "0330", "123");
    }
}
