#!/usr/bin/env zsh

bundle update --all
bundle install
bundle exec gem install fastlane
bundle update fastlane
bundle exec fastlane run update_fastlane
bundle exec fastlane update_plugins
(
  cd docs;
  hugo mod get -u
  npm upgrade
)
./gradlew -Dorg.gradle.configuration-cache=false versionCatalogUpdate --check
./gradlew versionCatalogFormat
./gradlew sortDependencies
