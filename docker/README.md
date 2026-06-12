# Local Device Farm (docker-android)

A self-hosted "device farm" of containerized Android emulators built on
[budtmo/docker-android](https://github.com/budtmo/docker-android) v2. Each container runs an
emulator with its own device profile plus an embedded Appium 2 server, so the exact same
Hermes test code targets any device simply by pointing `APPIUM_URL` at that container's
Appium port — no code or capability changes.

## Hard requirement: Linux host with KVM

These images need hardware-accelerated virtualization via `/dev/kvm`. Be aware, honestly:

- **Does NOT work on Windows 10 Home.** WSL2 does not expose nested virtualization, so
  there is no `/dev/kvm` inside Docker Desktop.
- **Does NOT work on macOS** (Docker runs in a VM without KVM passthrough).
- **Works on:** bare-metal Linux, Linux VPS/cloud instances with nested virtualization
  enabled, and GitHub Actions `ubuntu-latest` runners (KVM is available there).

Verify on the host before starting: `ls /dev/kvm` must succeed.

## Running the farm

```bash
cd docker
docker compose up -d
```

Two devices boot (first start takes a few minutes — wait for the healthchecks):

| Container       | Device profile     | noVNC (watch in browser) | Appium                  |
| --------------- | ------------------ | ------------------------ | ----------------------- |
| hermes-device-1 | Samsung Galaxy S10 | http://localhost:6080    | http://localhost:4723   |
| hermes-device-2 | Nexus 5            | http://localhost:6081    | http://localhost:4724   |

Open the noVNC URLs in a browser to watch each emulator live while tests run.

## Running the suite against a device

The compose file mounts the project's `apps/` directory into each container at
`/opt/hermes/apps`, because the Appium server (inside the container) must be able to read
the APK. Use the `grid` profile and override the app path accordingly:

```bash
# Device 1 (Samsung Galaxy S10)
APPIUM_URL=http://localhost:4723 \
APP_PATH=/opt/hermes/apps/Android-MyDemoAppRN.1.3.0.build-244.apk \
mvn test -Dhermes.env=grid

# Device 2 (Nexus 5)
APPIUM_URL=http://localhost:4724 \
APP_PATH=/opt/hermes/apps/Android-MyDemoAppRN.1.3.0.build-244.apk \
mvn test -Dhermes.env=grid
```

Run both commands in parallel (separate terminals or CI jobs) for a simple form of
device-matrix execution.

## Scaling and other devices

Add more devices by copying a service block in `docker-compose.yml` with a new container
name, a different `EMULATOR_DEVICE` value and unique host ports (e.g. `6082`/`4725`).
Other Android versions are available as image tags (`emulator_12.0`, `emulator_13.0`, ...).

The list of supported device profiles (Samsung Galaxy S6–S10, Nexus 4/5/7, pixel-class
profiles, etc.) is in the upstream project: https://github.com/budtmo/docker-android

## Tearing down

```bash
docker compose down
```
