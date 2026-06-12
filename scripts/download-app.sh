#!/usr/bin/env bash
# Downloads the pinned SUT build (Sauce Labs My Demo App RN v1.3.0) into apps/ (gitignored).
set -euo pipefail

APP_URL="https://github.com/saucelabs/my-demo-app-rn/releases/download/v1.3.0/Android-MyDemoAppRN.1.3.0.build-244.apk"
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
APP_DIR="$ROOT_DIR/apps"
APP_FILE="$APP_DIR/Android-MyDemoAppRN.1.3.0.build-244.apk"

mkdir -p "$APP_DIR"

if [ -f "$APP_FILE" ]; then
  echo "APK already present: $APP_FILE"
  exit 0
fi

CURL_EXTRA_OPTS=()
case "${OSTYPE:-}" in
  msys* | cygwin*)
    # Windows schannel: AV products that intercept TLS break certificate
    # revocation checks; skipping the check keeps the download working.
    CURL_EXTRA_OPTS+=(--ssl-no-revoke)
    ;;
esac

echo "Downloading $APP_URL"
curl --fail --location --retry 3 "${CURL_EXTRA_OPTS[@]}" --output "$APP_FILE" "$APP_URL"
echo "Saved to $APP_FILE"
