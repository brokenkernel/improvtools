#!/usr/bin/env zsh

./gradlew ktlintFormat
./gradlew ktlintGenerateBaseline
./gradlew updateLintBaseline
