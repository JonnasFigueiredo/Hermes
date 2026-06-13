package io.hermes.core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

/**
 * Creates platform-specific drivers for the SUT. The platform is selected by
 * configuration ({@code -Dplatform=android|ios}); everything above this class
 * works with the {@link AppiumDriver} abstraction.
 */
public final class DriverFactory {

    private DriverFactory() {
    }

    public static AppiumDriver createDriver() {
        return switch (Config.platform()) {
            case ANDROID -> new AndroidDriver(serverUrl(), androidOptions());
            case IOS -> new IOSDriver(serverUrl(), iosOptions());
        };
    }

    private static UiAutomator2Options androidOptions() {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName(Config.deviceName())
                .setApp(Config.appPath())
                .setAutoGrantPermissions(true)
                .setNewCommandTimeout(Duration.ofMinutes(3))
                .setUiautomator2ServerLaunchTimeout(Duration.ofMinutes(2))
                .setAdbExecTimeout(Duration.ofMinutes(2));
        applyExtraCapabilities(options::setCapability);
        return options;
    }

    private static XCUITestOptions iosOptions() {
        XCUITestOptions options = new XCUITestOptions()
                .setDeviceName(Config.deviceName())
                .setApp(Config.appPath())
                .setNewCommandTimeout(Duration.ofMinutes(5))
                // First CI run compiles WebDriverAgent from scratch — give it room.
                .setWdaLaunchTimeout(Duration.ofMinutes(8))
                .setWdaConnectionTimeout(Duration.ofMinutes(8))
                .setWdaStartupRetries(4)
                .setWdaStartupRetryInterval(Duration.ofSeconds(20))
                // A fresh WDA per session avoids the intermittent ECONNREFUSED:8100
                // seen when a stale agent lingers on the CI simulator.
                .setUseNewWDA(true);
        applyExtraCapabilities(options::setCapability);
        return options;
    }

    /** Grid/farm profiles add provider-specific capabilities without code changes. */
    private static void applyExtraCapabilities(CapabilitySetter setter) {
        for (Map.Entry<String, String> capability : Config.extraCapabilities().entrySet()) {
            setter.set(capability.getKey(), capability.getValue());
        }
    }

    private static URL serverUrl() {
        try {
            return URI.create(Config.appiumUrl()).toURL();
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid APPIUM_URL: " + Config.appiumUrl(), e);
        }
    }

    @FunctionalInterface
    private interface CapabilitySetter {
        void set(String name, String value);
    }
}
