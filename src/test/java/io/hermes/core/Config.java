package io.hermes.core;

import java.nio.file.Paths;

/**
 * Centralized configuration resolved from environment variables with sane local defaults.
 */
public final class Config {

    public static final String APP_PACKAGE = "com.saucelabs.mydemoapp.rn";

    private Config() {
    }

    public static String appiumUrl() {
        return env("APPIUM_URL", "http://127.0.0.1:4723");
    }

    public static String appPath() {
        String path = env("APP_PATH", "apps/Android-MyDemoAppRN.1.3.0.build-244.apk");
        return Paths.get(path).toAbsolutePath().normalize().toString();
    }

    public static String deviceName() {
        return env("DEVICE_NAME", "Android Emulator");
    }

    private static String env(String name, String defaultValue) {
        String value = System.getenv(name);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }
}
