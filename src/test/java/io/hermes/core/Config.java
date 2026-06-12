package io.hermes.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * Centralized configuration with execution profiles. The same suite targets a local
 * emulator, a containerized grid or a cloud device farm purely by configuration.
 *
 * <p>Resolution order for every key (e.g. {@code appium.url}):</p>
 * <ol>
 *   <li>JVM system property: {@code -Dappium.url=...}</li>
 *   <li>Environment variable (upper snake case): {@code APPIUM_URL}</li>
 *   <li>Profile file on the classpath: {@code config/<env>.properties}</li>
 *   <li>Built-in default</li>
 * </ol>
 *
 * <p>The active profile is selected with {@code -Dhermes.env} / {@code HERMES_ENV}
 * (default {@code local}).</p>
 */
public final class Config {

    /** Android applicationId and iOS bundleId — the SUT uses the same value on both. */
    public static final String APP_ID = "com.saucelabs.mydemoapp.rn";

    private static final String CAPABILITY_PREFIX = "capability.";
    private static final Properties PROFILE = loadProfile(activeProfile());

    private Config() {
    }

    public static Platform platform() {
        return Platform.from(get("platform", "android"));
    }

    public static String activeProfile() {
        String fromProperty = System.getProperty("hermes.env");
        if (fromProperty != null && !fromProperty.isBlank()) {
            return fromProperty;
        }
        String fromEnv = System.getenv("HERMES_ENV");
        return (fromEnv == null || fromEnv.isBlank()) ? "local" : fromEnv;
    }

    public static String appiumUrl() {
        return get("appium.url", "http://127.0.0.1:4723");
    }

    public static String appPath() {
        String defaultPath = platform() == Platform.IOS
                ? "apps/MyRNDemoApp.app"
                : "apps/Android-MyDemoAppRN.1.3.0.build-244.apk";
        String path = get("app.path", defaultPath);
        // Farm profiles reference a pre-uploaded app id (e.g. bs://...) instead of a file
        if (path.contains("://")) {
            return path;
        }
        return Paths.get(path).toAbsolutePath().normalize().toString();
    }

    public static String deviceName() {
        String defaultName = platform() == Platform.IOS ? "iPhone 15" : "Android Emulator";
        return get("device.name", defaultName);
    }

    /** Default explicit-wait timeout; CI environments override it for slower emulators. */
    public static java.time.Duration defaultTimeout() {
        return java.time.Duration.ofSeconds(Long.parseLong(get("timeout.default", "15")));
    }

    /** Short timeout for presence/absence probes. */
    public static java.time.Duration shortTimeout() {
        return java.time.Duration.ofSeconds(Long.parseLong(get("timeout.short", "5")));
    }

    /**
     * Extra Appium capabilities declared with the {@code capability.} prefix in the
     * active profile, overridable per run via system properties
     * (e.g. {@code -Dcapability.appium:platformVersion=13.0}). This is what lets grid
     * and device-farm targets add provider-specific capabilities without code changes.
     */
    public static Map<String, String> extraCapabilities() {
        Map<String, String> capabilities = new HashMap<>();
        for (String key : PROFILE.stringPropertyNames()) {
            if (key.startsWith(CAPABILITY_PREFIX)) {
                capabilities.put(key.substring(CAPABILITY_PREFIX.length()), PROFILE.getProperty(key));
            }
        }
        for (String key : System.getProperties().stringPropertyNames()) {
            if (key.startsWith(CAPABILITY_PREFIX)) {
                capabilities.put(key.substring(CAPABILITY_PREFIX.length()), System.getProperty(key));
            }
        }
        return capabilities;
    }

    private static String get(String key, String defaultValue) {
        String fromProperty = System.getProperty(key);
        if (fromProperty != null && !fromProperty.isBlank()) {
            return fromProperty;
        }
        String fromEnv = System.getenv(toEnvName(key));
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv;
        }
        String fromProfile = PROFILE.getProperty(key);
        return (fromProfile == null || fromProfile.isBlank()) ? defaultValue : fromProfile;
    }

    private static String toEnvName(String key) {
        return key.replace('.', '_').toUpperCase(Locale.ROOT);
    }

    private static Properties loadProfile(String env) {
        Properties properties = new Properties();
        String resource = "config/" + env + ".properties";
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream(resource)) {
            if (in == null) {
                throw new IllegalStateException("Execution profile not found on classpath: " + resource);
            }
            properties.load(in);
            return properties;
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read execution profile " + resource, e);
        }
    }
}
