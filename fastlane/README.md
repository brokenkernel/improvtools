fastlane documentation
----

# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```sh
xcode-select --install
```

For _fastlane_ installation instructions, see [Installing _fastlane_](https://docs.fastlane.tools/#installing-fastlane)

# Available Actions

## Android

### android test

```sh
[bundle exec] fastlane android test
```

Runs all the tests

### android beta

```sh
[bundle exec] fastlane android beta
```

Submit a new Beta Build to Crashlytics Beta

### android deploy

```sh
[bundle exec] fastlane android deploy
```

Deploy a new version to the Google Play

### android build_for_screengrab

```sh
[bundle exec] fastlane android build_for_screengrab
```

Build debug and test APK for screenshots

### android increment_gradle_version

```sh
[bundle exec] fastlane android increment_gradle_version
```

Bump Version In Gradle File

### android improv_tools_screenshots

```sh
[bundle exec] fastlane android improv_tools_screenshots
```

Take Screenshots

### android playstore

```sh
[bundle exec] fastlane android playstore
```

Full Play Store Deployment

----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
