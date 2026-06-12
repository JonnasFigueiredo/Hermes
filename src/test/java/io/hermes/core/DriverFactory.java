package io.hermes.core;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

/**
 * Creates {@link AndroidDriver} instances configured for the SUT.
 */
public final class DriverFactory {

    private DriverFactory() {
    }

    public static AndroidDriver createAndroidDriver() {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName(Config.deviceName())
                .setApp(Config.appPath())
                .setAutoGrantPermissions(true)
                .setNewCommandTimeout(Duration.ofMinutes(3))
                .setUiautomator2ServerLaunchTimeout(Duration.ofMinutes(2))
                .setAdbExecTimeout(Duration.ofMinutes(2));

        // Grid/farm profiles add provider-specific capabilities without code changes
        Config.extraCapabilities().forEach(options::setCapability);

        try {
            return new AndroidDriver(URI.create(Config.appiumUrl()).toURL(), options);
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid APPIUM_URL: " + Config.appiumUrl(), e);
        }
    }
}
