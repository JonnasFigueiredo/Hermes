package io.hermes.screens;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.hermes.screens.components.NavBar;
import org.openqa.selenium.By;

public class LoginScreen extends BaseScreen {

    private static final By USERNAME_FIELD = AppiumBy.accessibilityId("Username input field");
    private static final By PASSWORD_FIELD = AppiumBy.accessibilityId("Password input field");
    private static final By LOGIN_BUTTON = AppiumBy.accessibilityId("Login button");
    private static final By GENERIC_ERROR_MESSAGE = AppiumBy.accessibilityId("generic-error-message");

    private final NavBar navBar;

    public LoginScreen(AndroidDriver driver) {
        super(driver);
        this.navBar = new NavBar(driver);
    }

    public NavBar navBar() {
        return navBar;
    }

    public boolean isLoaded() {
        return isVisible(LOGIN_BUTTON);
    }

    public void loginAs(String username, String password) {
        type(USERNAME_FIELD, username);
        type(PASSWORD_FIELD, password);
        tap(LOGIN_BUTTON);
    }

    public String errorMessage() {
        return nonBlankTextOf(GENERIC_ERROR_MESSAGE);
    }
}
