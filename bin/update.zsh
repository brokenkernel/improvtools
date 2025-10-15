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

./gradlew dependencyUpdates --no-parallel
./gradlew versionCatalogFormat
./gradlew sortDependencies
