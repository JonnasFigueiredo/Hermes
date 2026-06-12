# Hermes

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Appium](https://img.shields.io/badge/Appium-2.x-purple?logo=appium)
![JUnit](https://img.shields.io/badge/JUnit-5-green?logo=junit5)
![License](https://img.shields.io/badge/License-MIT-blue)

Mobile E2E test framework for Android, built with **Appium 2, Java 21, Maven and JUnit 5**.
The system under test is the [Sauce Labs My Demo App RN](https://github.com/saucelabs/my-demo-app-rn) v1.3.0 (pinned APK).

## What this project demonstrates

- **Screen Object Model with composition** — screens (`LoginScreen`, `CatalogScreen`, `ProductScreen`, `CartScreen`) share a `NavBar` component instead of relying on inheritance chains.
- **Deterministic state reset** — before every test the app is terminated, relaunched and reset through the SUT's documented long-press on the header logo, keeping tests fully independent.
- **Modern native gestures** — long-press and scroll implemented with `mobile:` commands (`mobile: longClickGesture`, `mobile: scrollGesture`); no legacy `TouchAction`.
- **Explicit waits only** — synchronization is done exclusively with `WebDriverWait`; there is no `Thread.sleep` in the codebase.
- **Failure evidence** — a JUnit 5 `TestWatcher` captures a PNG screenshot to `target/screenshots/` and attaches it to the Allure report on every test failure.
- **CI with a real emulator** — GitHub Actions boots a hardware-accelerated (KVM) Android emulator on `ubuntu-latest` and runs the full suite headlessly.

## Project structure

```
src/test/java/io/hermes/
├── core/        Config, DriverFactory, Gestures, BaseTest, ScreenshotOnFailure
├── screens/     BaseScreen, LoginScreen, CatalogScreen, ProductScreen, CartScreen
│   └── components/  NavBar
└── tests/       LoginTest, CatalogTest, CartTest
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

# Or only the smoke subset
mvn test -Dgroups=smoke
```

## Configuration

| Env var       | Default                                       | Description                          |
| ------------- | --------------------------------------------- | ------------------------------------ |
| `APPIUM_URL`  | `http://127.0.0.1:4723`                       | Appium server URL                    |
| `APP_PATH`    | `apps/Android-MyDemoAppRN.1.3.0.build-244.apk` | Path to the APK under test           |
| `DEVICE_NAME` | `Android Emulator`                            | Device name passed to UiAutomator2   |

## CI

The [workflow](.github/workflows/mobile-tests.yml) has two jobs:

1. **Compile gate** — fast `mvn test-compile` to fail early on broken code.
2. **E2E** — enables KVM, installs Appium + UiAutomator2, boots an API 30 x86_64 emulator via `reactivecircus/android-emulator-runner` and runs `mvn test`. Allure results are always uploaded as artifacts; screenshots and the Appium log are uploaded on failure.

## Roadmap

- iOS suite on a macOS runner
- Checkout flow coverage
- BrowserStack open-source plan integration
- Allure report published to GitHub Pages

## License

[MIT](LICENSE)
