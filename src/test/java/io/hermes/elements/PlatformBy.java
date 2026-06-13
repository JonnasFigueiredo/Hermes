package io.hermes.elements;

import io.hermes.core.Config;
import io.hermes.core.Platform;
import org.openqa.selenium.By;

/**
 * Resolves a locator to the right value for the platform under test. Most elements of
 * the SUT share the same accessibility id on Android and iOS, so {@link #shared} is the
 * common case; {@link #of} is used only where the platforms genuinely diverge.
 *
 * <p>Locators are evaluated once at class-load time, when the run's platform is already
 * fixed, so there is no per-call overhead.</p>
 */
public final class PlatformBy {

    private PlatformBy() {
    }

    /** Same accessibility id on both platforms. */
    public static By shared(String accessibilityId) {
        return io.appium.java_client.AppiumBy.accessibilityId(accessibilityId);
    }

    /** Different locators per platform. */
    public static By of(By android, By ios) {
        return Config.platform() == Platform.IOS ? ios : android;
    }
}
