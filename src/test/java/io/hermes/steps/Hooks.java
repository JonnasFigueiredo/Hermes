package io.hermes.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Scenario;
import io.hermes.core.DriverManager;
import org.openqa.selenium.OutputType;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Hooks {

    private static final Path SCREENSHOT_DIR = Path.of("target", "screenshots");

    @BeforeAll
    public static void startDriver() {
        DriverManager.start();
    }

    @AfterAll
    public static void stopDriver() {
        DriverManager.stop();
    }

    @Before
    public void resetAppState() {
        DriverManager.resetAppState();
    }

    /**
     * On scenario failure saves a PNG under {@code target/screenshots/} and attaches it
     * to the scenario, which the Allure Cucumber plugin picks up for the report.
     */
    @After
    public void captureFailureEvidence(Scenario scenario) {
        if (!scenario.isFailed()) {
            return;
        }
        var driver = DriverManager.getDriver();
        String name = scenario.getName().replaceAll("[^A-Za-z0-9-]+", "_");
        byte[] png = driver.getScreenshotAs(OutputType.BYTES);
        try {
            Files.createDirectories(SCREENSHOT_DIR);
            Files.write(SCREENSHOT_DIR.resolve(name + ".png"), png);
            // The page source is the fastest way to see how the SUT exposes elements on
            // each platform — invaluable when porting selectors to iOS.
            Files.writeString(SCREENSHOT_DIR.resolve(name + ".xml"), driver.getPageSource());
        } catch (IOException e) {
            throw new UncheckedIOException("Could not save failure evidence for " + name, e);
        }
        scenario.attach(png, "image/png", name);
    }
}
