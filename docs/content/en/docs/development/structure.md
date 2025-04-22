---
title: "Code Structure"
---

The code is structured as follows. It isn't perfect (as there are not yet any tests) but should be generally followed.

Prefer dividing code by use case first and then by "type". For example prefer `encyclopedia/viewModels` instead of the reverse.

See [naming]({{< relref "naming#serialisable" >}}) for more details on naming.

## Component Structure

Each component should be isolated from each other and not directly depend on anything outside of specific directories.
Frustratingly kotlin does not make this easy to enforce.

### data

This should have no dependency on Compose whatsoever

#### model

- Should not depend on _android_ libraries at all

#### repository

### presentation

#### view

#### viewmodel

### sidecar

### section

Contains special information for external consumption. Exception to the isolated rule. For example `application` depends
on it. Other sections may use data here to navigate.

### api

If the component provides information to other component.

## Special Components

### application

This is the high level application. It can depend on nearly anything else but should likely limit itself to the
`section` and `api` and similar. Long term, this can be enforced. Nothing _should_ depend on this.

### infrastructure

Low level components used to build the application. Only `application` should directly depend on this but may provide
Singletons for everything else.

### components

Components and utilities useful for all Components.

### common

Utilities, state, etc, that all Components rely on. Does not currently exist and merged with `application`

```

TODO: make into chart
TODO: make into multi-module project so that `internal` actually helps.
