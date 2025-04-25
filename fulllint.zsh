#!/usr/bin/env zsh

./gradlew sortDependencies
./gradlew ktlintFormat
./gradlew ktlintGenerateBaseline
./gradlew updateLintBaseline
