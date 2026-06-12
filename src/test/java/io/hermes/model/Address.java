package io.hermes.model;

/**
 * Shipping address used on the checkout flow.
 */
public record Address(String fullName, String addressLine1, String city, String zipCode, String country) {

    public static Address valid() {
        return new Address("Bob Builder", "123 Main Street", "Sao Paulo", "04510001", "Brazil");
    }
}
