#!/usr/bin/env zsh

./gradlew sortDependencies
./gradlew versionCatalogFormat
./gradlew ktlintFormat
./gradlew ktlintGenerateBaseline
./gradlew updateLintBaseline
./gradlew lint
