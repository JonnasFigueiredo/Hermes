package io.hermes.core;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * JUnit 5 watcher: on test failure saves a PNG under {@code target/screenshots/}
 * and attaches it to the Allure report.
 */
public class ScreenshotOnFailure implements TestWatcher {

    private static final Path SCREENSHOT_DIR = Path.of("target", "screenshots");

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        var driver = BaseTest.getDriver();
        if (driver == null) {
            return;
        }
        byte[] png = driver.getScreenshotAs(OutputType.BYTES);
        String name = context.getRequiredTestClass().getSimpleName()
                + "_" + context.getRequiredTestMethod().getName();
        try {
            Files.createDirectories(SCREENSHOT_DIR);
            Files.write(SCREENSHOT_DIR.resolve(name + ".png"), png);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not save failure screenshot for " + name, e);
        }
        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(png), ".png");
    }
}
