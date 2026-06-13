package io.hermes.pages.components;

import io.appium.java_client.AppiumDriver;
import io.hermes.core.NativeDialogs;
import io.hermes.elements.LoginElements;
import io.hermes.elements.NavBarElements;
import io.hermes.pages.BasePage;

import java.time.Duration;

/**
 * Header bar + drawer menu, shared by every page via composition.
 */
public class NavBar extends BasePage {

    public NavBar(AppiumDriver driver) {
        super(driver);
    }

    public void openMenu() {
        tap(NavBarElements.OPEN_MENU_BUTTON);
    }

    /**
     * Opens the login screen through the drawer. If a previous scenario leaked a
     * logged-in session, the SUT redirects the login entry elsewhere; in that case
     * we log out and retry once so the login form is actually shown.
     */
    public void openLoginFromMenu() {
        openMenu();
        tap(NavBarElements.MENU_ITEM_LOG_IN);
        if (isVisible(LoginElements.LOGIN_BUTTON, SHORT_TIMEOUT)) {
            return;
        }
        // Leaked logged-in session: log out and try again.
        openMenu();
        if (isVisible(NavBarElements.MENU_ITEM_LOG_OUT, SHORT_TIMEOUT)) {
            tap(NavBarElements.MENU_ITEM_LOG_OUT);
            confirmLogout();
            openMenu();
        }
        tap(NavBarElements.MENU_ITEM_LOG_IN);
    }

    public void openCatalogFromMenu() {
        openMenu();
        tap(NavBarElements.MENU_ITEM_CATALOG);
    }

    /** Logs out through the drawer, accepting the confirmation dialogs. */
    public void logoutFromMenu() {
        openMenu();
        tap(NavBarElements.MENU_ITEM_LOG_OUT);
        confirmLogout();
    }

    private void confirmLogout() {
        // logout chains a confirmation and an acknowledge dialog
        if (NativeDialogs.acceptIfPresent(driver, Duration.ofSeconds(5))) {
            NativeDialogs.acceptIfPresent(driver, Duration.ofSeconds(3));
        }
    }

    public void openCart() {
        tap(NavBarElements.CART_BADGE);
    }

    /** Text shown on the cart badge, e.g. "1" when one item is in the cart. */
    public String cartBadgeText() {
        return nonBlankTextOf(NavBarElements.CART_BADGE);
    }

    /**
     * Waits until the badge shows the expected count. The badge transitions through
     * intermediate values (e.g. "1" right before a second item lands), so asserting
     * on the first non-blank read would race on slower devices.
     */
    public boolean cartBadgeShows(String expected) {
        return waitTextEquals(NavBarElements.CART_BADGE, expected);
    }
}
