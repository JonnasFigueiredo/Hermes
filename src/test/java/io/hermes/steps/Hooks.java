package io.hermes.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Scenario;
import io.hermes.core.DriverManager;
import io.hermes.pages.components.NavBar;
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
        new NavBar(DriverManager.getDriver()).ensureLoggedOut();
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
        byte[] png = DriverManager.getDriver().getScreenshotAs(OutputType.BYTES);
        String name = scenario.getName().replaceAll("[^A-Za-z0-9-]+", "_");
        try {
            Files.createDirectories(SCREENSHOT_DIR);
            Files.write(SCREENSHOT_DIR.resolve(name + ".png"), png);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not save failure screenshot for " + name, e);
        }
        scenario.attach(png, "image/png", name);
    }
}
