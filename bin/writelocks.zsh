#!/usr/bin/env zsh

./gradlew :app:build :components:build :app:hiltJavaCompileDebugAndroidTest projectHealth dependencyUpdates --write-locks
