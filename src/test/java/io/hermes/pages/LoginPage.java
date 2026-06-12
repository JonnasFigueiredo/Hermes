package io.hermes.pages;

import io.appium.java_client.android.AndroidDriver;
import io.hermes.elements.LoginElements;
import io.hermes.model.User;
import io.hermes.pages.components.NavBar;

public class LoginPage extends BasePage {

    private final NavBar navBar;

    public LoginPage(AndroidDriver driver) {
        super(driver);
        this.navBar = new NavBar(driver);
    }

    public NavBar navBar() {
        return navBar;
    }

    /** Navigates to the login screen through the drawer menu. */
    public LoginPage open() {
        navBar.openLoginFromMenu();
        return this;
    }

    public boolean isLoaded() {
        return isVisible(LoginElements.LOGIN_BUTTON);
    }

    public void loginAs(User user) {
        type(LoginElements.USERNAME_FIELD, user.username());
        type(LoginElements.PASSWORD_FIELD, user.password());
        submit();
    }

    public void loginWithUsernameOnly(String username) {
        type(LoginElements.USERNAME_FIELD, username);
        submit();
    }

    public void submit() {
        tap(LoginElements.LOGIN_BUTTON);
    }

    public String genericErrorMessage() {
        return nonBlankTextOf(LoginElements.GENERIC_ERROR);
    }

    public String usernameFieldError() {
        return nonBlankTextOf(LoginElements.USERNAME_FIELD_ERROR);
    }

    public String passwordFieldError() {
        return nonBlankTextOf(LoginElements.PASSWORD_FIELD_ERROR);
    }
}
