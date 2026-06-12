#!/usr/bin/env bash
# Downloads the pinned SUT build (Sauce Labs My Demo App RN v1.3.0) into apps/ (gitignored).
# Usage: download-app.sh [android|ios]   (default: android)
set -euo pipefail

PLATFORM="${1:-android}"
RELEASE_BASE="https://github.com/saucelabs/my-demo-app-rn/releases/download/v1.3.0"
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
APP_DIR="$ROOT_DIR/apps"

mkdir -p "$APP_DIR"

CURL_EXTRA_OPTS=()
case "${OSTYPE:-}" in
  msys* | cygwin*)
    # Windows schannel: AV products that intercept TLS break certificate
    # revocation checks; skipping the check keeps the download working.
    CURL_EXTRA_OPTS+=(--ssl-no-revoke)
    ;;
esac

download() {
  local url="$1" target="$2"
  if [ -e "$target" ]; then
    echo "Already present: $target"
    return 0
  fi
  echo "Downloading $url"
  # ${arr[@]+...} keeps macOS bash 3.2 happy: expanding an empty array under
  # `set -u` is an "unbound variable" error there.
  curl --fail --location --retry 3 ${CURL_EXTRA_OPTS[@]+"${CURL_EXTRA_OPTS[@]}"} --output "$target" "$url"
  echo "Saved to $target"
}

case "$PLATFORM" in
  android)
    download "$RELEASE_BASE/Android-MyDemoAppRN.1.3.0.build-244.apk" \
             "$APP_DIR/Android-MyDemoAppRN.1.3.0.build-244.apk"
    ;;
  ios)
    ZIP_FILE="$APP_DIR/iOS-Simulator-MyRNDemoApp.1.3.0-162.zip"
    if [ -d "$APP_DIR/MyRNDemoApp.app" ]; then
      echo "Already present: $APP_DIR/MyRNDemoApp.app"
      exit 0
    fi
    download "$RELEASE_BASE/iOS-Simulator-MyRNDemoApp.1.3.0-162.zip" "$ZIP_FILE"
    unzip -oq "$ZIP_FILE" -d "$APP_DIR"
    rm -f "$ZIP_FILE"
    echo "Extracted simulator app(s):"
    find "$APP_DIR" -maxdepth 2 -name "*.app" -type d
    ;;
  *)
    echo "Unknown platform '$PLATFORM' — use android or ios" >&2
    exit 1
    ;;
esac
