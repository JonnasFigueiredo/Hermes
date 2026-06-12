# Hermes

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Appium](https://img.shields.io/badge/Appium-2.x-purple?logo=appium)
![Cucumber](https://img.shields.io/badge/Cucumber-7-brightgreen?logo=cucumber)
![JUnit](https://img.shields.io/badge/JUnit-5-green?logo=junit5)
![Allure](https://img.shields.io/badge/Allure-Report-yellow)
![License](https://img.shields.io/badge/License-MIT-blue)

Mobile E2E test framework for Android, built with **Appium 2, Java 21, Maven, Cucumber (BDD) and JUnit 5**, with **Allure** reporting.
The system under test is the [Sauce Labs My Demo App RN](https://github.com/saucelabs/my-demo-app-rn) v1.3.0 (pinned APK).
Gherkin scenarios are written in Brazilian Portuguese (`# language: pt`).

## What this project demonstrates

- **BDD with Cucumber** — 21 scenarios across 5 features (login, catalog, product details, cart and the full checkout flow), including `Esquema do Cenário` (Scenario Outline) with example tables.
- **Page Objects with composition** — each screen has a *page* (behavior) backed by an *elements* class (locators only); every page shares the `NavBar` component by composition instead of inheritance chains.
- **Reusable steps with parameters** — Cucumber expressions like `o usuário adiciona o produto {int} ao carrinho` keep the step vocabulary small and composable across features.
- **Deterministic state reset** — before every scenario the app is terminated, relaunched and reset through the SUT's documented long-press on the header logo, keeping scenarios fully independent.
- **Modern native gestures** — long-press and scroll implemented with `mobile:` commands (`mobile: longClickGesture`, `mobile: scrollGesture`); no legacy `TouchAction`.
- **Explicit waits only** — synchronization is done exclusively with `WebDriverWait`; there is no `Thread.sleep` in the codebase.
- **Failure evidence** — a Cucumber hook captures a PNG to `target/screenshots/` and attaches it to the Allure report whenever a scenario fails.
- **CI with a real emulator** — GitHub Actions boots a hardware-accelerated (KVM) Android emulator on `ubuntu-latest` and runs the suite headlessly.

## Project structure

```
src/test/java/io/hermes/
├── core/        Config, DriverFactory, DriverManager, Gestures, NativeDialogs
├── elements/    One locator-only class per screen (AppiumBy constants)
├── pages/       Page objects: Login, Catalog, Product, Cart, Checkout (address,
│   │            payment, review, complete) — actions and assertions helpers
│   └── components/  NavBar (header + drawer), shared via composition
├── model/       Test data: User, Address, PaymentCard
├── steps/       Hooks + step definitions (pt-BR) per feature
└── RunCucumberTest.java   JUnit Platform suite runner

src/test/resources/features/   login, catalog, product, cart, checkout (.feature, pt-BR)
```

## Running locally

Prerequisites: JDK 21, Maven, Node.js, Android SDK with an emulator (or a real device), and Appium 2 with the UiAutomator2 driver:

```bash
npm install -g appium
appium driver install uiautomator2
```

Then:

```bash
# 1. Download the pinned APK into apps/ (gitignored)
bash scripts/download-app.sh

# 2. Start an emulator and the Appium server
appium &

# 3. Run the full suite
mvn test

# Only the smoke subset (also works with -Dcucumber.filter.tags="@smoke")
mvn test -Dgroups=smoke

# Validate step bindings without a device
mvn test -Dcucumber.execution.dry-run=true
```

In Eclipse/IntelliJ, run `RunCucumberTest` as a JUnit test.

## Reports

Two reports come out of every run (including runs started from the IDE):

- **Instant HTML (Cucumber)** — generated during the run at
  `target/cucumber-report/cucumber.html`; just open it in a browser. A
  `cucumber.json` is written alongside for integrations.
- **Allure** (deep analysis: steps, attachments, trends) — results land in
  `target/allure-results`; build and open with:

```bash
mvn allure:serve    # generates and opens in the browser
mvn allure:report   # just generates (target/site/allure-maven-plugin)
```

Failed scenarios include the failure screenshot as an attachment in both reports.

## Configuration & execution profiles

The suite never knows where it runs — the execution target is pure configuration
(see [ROADMAP.md](ROADMAP.md) for the architecture principles). A profile is selected
with `-Dhermes.env=<name>` (or `HERMES_ENV`), loading `src/test/resources/config/<name>.properties`:

| Profile | Target |
| ------- | ------ |
| `local` (default) | Emulator/device + Appium on this machine |
| `grid`  | Containerized devices ([docker-android](docker/README.md), Linux/KVM hosts) |
| `farm`  | Cloud device farm (BrowserStack App Automate shape; credentials via env vars only) |

Every key can be overridden per run — precedence: system property > env var > profile file > default:

| Key (`-D`)    | Env var       | Default                                        | Description                        |
| ------------- | ------------- | ---------------------------------------------- | ---------------------------------- |
| `appium.url`  | `APPIUM_URL`  | `http://127.0.0.1:4723`                        | Appium server / grid / farm hub URL |
| `app.path`    | `APP_PATH`    | `apps/Android-MyDemoAppRN.1.3.0.build-244.apk` | APK path, or farm app id (`bs://...`) |
| `device.name` | `DEVICE_NAME` | `Android Emulator`                             | Device name capability             |

Profiles may also declare provider-specific Appium capabilities with the
`capability.` prefix (e.g. `capability.appium:platformVersion=13.0`), forwarded
as-is by the `DriverFactory` — integrating a new grid or farm requires zero code.

## CI

The [workflow](.github/workflows/mobile-tests.yml) has three jobs:

1. **Compile gate** — `mvn test-compile` + Cucumber dry-run to fail early on broken code or unbound steps.
2. **E2E (device matrix)** — enables KVM and runs the suite on a matrix of emulators (API 30/pixel_4 and API 33/pixel_6) via `reactivecircus/android-emulator-runner`: same code, different devices. Allure results are always uploaded; screenshots and the Appium log on failure.
3. **Report** — merges Allure results from every device and publishes the report (with run history) to GitHub Pages.

Runs can also be triggered manually from the Actions tab (`workflow_dispatch`) choosing the suite: `all`, `smoke` or `regression`.

## Local device grid (Docker)

[docker/](docker/README.md) ships a compose file that boots multiple containerized
emulators with different device profiles (docker-android). It requires a **Linux host
with KVM** — it does not work on Windows/macOS hosts; see the folder README.

## Roadmap

See [ROADMAP.md](ROADMAP.md) — principles and phases (device farm profile, iOS on a macOS runner, ReportPortal).

## License

[MIT](LICENSE)
