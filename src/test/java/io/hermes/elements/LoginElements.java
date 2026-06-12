package io.hermes.elements;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/** Locators of the login screen. */
public final class LoginElements {

    public static final By SCREEN = AppiumBy.accessibilityId("login screen");
    public static final By USERNAME_FIELD = AppiumBy.accessibilityId("Username input field");
    public static final By PASSWORD_FIELD = AppiumBy.accessibilityId("Password input field");
    public static final By LOGIN_BUTTON = AppiumBy.accessibilityId("Login button");

    public static final By GENERIC_ERROR = AppiumBy.accessibilityId("generic-error-message");
    public static final By USERNAME_FIELD_ERROR = AppiumBy.accessibilityId("Username-error-message");
    public static final By PASSWORD_FIELD_ERROR = AppiumBy.accessibilityId("Password-error-message");

    private LoginElements() {
    }
}
