package io.hermes.core;

import java.util.Locale;

/** Mobile platform under test. */
public enum Platform {
    ANDROID,
    IOS;

    public static Platform from(String value) {
        return switch (value.toLowerCase(Locale.ROOT)) {
            case "android" -> ANDROID;
            case "ios" -> IOS;
            default -> throw new IllegalArgumentException(
                    "Unknown platform '" + value + "' — use android or ios");
        };
    }
}
