#!/usr/bin/env zsh

bundle update
bundle install
bundle exec fastlane run update_fastlane
(
  cd docs;
  hugo mod get -u
)
