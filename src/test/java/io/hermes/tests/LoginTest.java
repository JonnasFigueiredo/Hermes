package io.hermes.tests;

import io.hermes.core.BaseTest;
import io.hermes.screens.CatalogScreen;
import io.hermes.screens.LoginScreen;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTest extends BaseTest {

    private static final String VALID_USERNAME = "bob@example.com";
    private static final String VALID_PASSWORD = "10203040";

    @Test
    @Tag("smoke")
    @DisplayName("Valid credentials log in and land on the catalog")
    void validLoginLandsOnCatalog() {
        LoginScreen loginScreen = new LoginScreen(driver);
        loginScreen.navBar().openLoginFromMenu();
        assertTrue(loginScreen.isLoaded(), "Login screen should be displayed");

        loginScreen.loginAs(VALID_USERNAME, VALID_PASSWORD);

        CatalogScreen catalog = new CatalogScreen(driver);
        assertTrue(catalog.isLoaded(), "Catalog should be displayed after a valid login");
    }

    @Test
    @Tag("regression")
    @DisplayName("Invalid credentials show an error message")
    void invalidLoginShowsError() {
        LoginScreen loginScreen = new LoginScreen(driver);
        loginScreen.navBar().openLoginFromMenu();
        assertTrue(loginScreen.isLoaded(), "Login screen should be displayed");

        loginScreen.loginAs(VALID_USERNAME, "wrong-password");

        String error = loginScreen.errorMessage();
        assertFalse(error.isBlank(), "An error message should be shown for invalid credentials");
    }
}
