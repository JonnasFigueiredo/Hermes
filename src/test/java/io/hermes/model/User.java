package io.hermes.model;

/**
 * Test users documented by the SUT (Sauce Labs My Demo App).
 */
public record User(String username, String password) {

    public static User standard() {
        return new User("bob@example.com", "10203040");
    }

    public static User lockedOut() {
        return new User("alice@example.com", "10203040");
    }

    public static User withWrongPassword() {
        return new User("bob@example.com", "senha-errada");
    }
}
