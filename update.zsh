#!/usr/bin/env zsh

bundle update
bundle install
bundle exec fastlane run update_fastlane
bundle exec fastlane update_plugins
(
  cd docs;
  hugo mod get -u
  npm upgrade
)

./gradlew dependencyUpdates
./gradlew versionCatalogFormat
./gradlew dependencies --write-locks
./gradlew :app:dependencies --write-locks
./gradlew :components:dependencies --write-locks
