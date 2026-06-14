# Hermes — Architecture Principles & Roadmap

## Principles

1. **Execution target is configuration, not code.** The suite never knows where it runs.
   Switching between a local emulator, a containerized grid or a cloud device farm is a
   matter of execution profiles (`-Dhermes.env=local|grid|farm`) and environment
   variables — never a code change.
2. **Orchestration belongs to CI.** GitHub Actions (`workflow_dispatch` with inputs +
   device matrix) is the control plane for triggering runs, picking suites and devices.
   No custom orchestrator is built or maintained here.
3. **Reporting is centralized and public.** Every run — from any target — produces
   Allure results that land in a single report with execution history.
4. **Integrate the existing ecosystem instead of reinventing it.** Selenium/Appium,
   docker-android, emulator runners, Allure, GitHub Pages: this project composes
   market-standard tools the way production teams do.
5. **Everything claimed is verifiable.** If it is in the README, it runs.

## Phases

| Phase | Deliverable | Status |
|---|---|---|
| 1 | BDD suite (21 scenarios, pt-BR Gherkin) green on a local emulator | **done** |
| 2 | CI on GitHub Actions: compile gate + emulator E2E, device matrix (API 30/33) | **done — green** |
| 3 | Allure report published to GitHub Pages with run history; `workflow_dispatch` with suite selector | **done** |
| 4 | Containerized device grid (docker-android) for Linux/KVM hosts | **done — green** (validated on CI) |
| 5 | Cloud device farm profile (BrowserStack App Automate, open-source plan) | profile + workflow ready, account/secrets pending |
| 6 | iOS suite on a macOS runner (XCUITest driver, same Gherkin) | **done — smoke green** (full checkout end-to-end on the iOS simulator) |
| 7 | Full iOS regression parity (sort, logout, guest-checkout on iOS) | next |
| 8 | Test observability (ReportPortal integration) | planned |
