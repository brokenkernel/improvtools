---
title: "Object Naming"
---

This is aspirational as I've tried a few different ways of doing this and not everything matches the naming scheme yet.

## Navigation

1. `Route` — This is a thing that can be visited via the navigation structure.
1. `Screen` — This is a user-visible description of a route. Contains things like "title" or menus
1. `Tab` — A top level Composable for the application. Typically stand alone.
1. `Section` - A high level grouping of `Screen`, `Route`, `Section`
1. `SectionNavigation` — Each high level component of the

## Injectables

1. `Repository` — A collection of related injections
1. `DefaultRepository` — the production copy of data used. May not exist and instead be something like
   `ResourcesRepository`, `NetworkRepository`, etc.

## Parseable / Serialisable {id=serialisable}

For data which needs to be serialised:

1. `__DatumOnDiskStructure` — the data structure closest to that which will be read.
1. `__DatumParsed` — the data in the form most useful for internal processing
1. `___DatumTransformed` - the data in middle or final format
1. `___DatumUI` — the data as presented to the UI. Should not be further transformed.

## Naming Convention

1. Prefer collections to have plurals. This makes looping with a singular slightly more readable. The class should be singular. For example:

```lang=kotlin

enum class NavigableScreen {
  ABC,
  DEF
  ;
}

val listOfScreens = listOf(ABC, DEF)

private fun loopScreens(screens: List[NavigableScreen]) {
  listOfScreens.forEach { screen ->
    // ...
  }
}

```

## Code Style & Convention

The following applies in order. Anything later overrides anything earlier

1. Use the official Kotlin style
2. Use the official Android style
3.
  A. Avoid expression body functions unless they are clearly more readable

  B. Avoid putting lambdas outside of the signature except for Compose or similar DSLs.

  C. Prefer trailing commas wherever possible. I'd like to exclude some specific cases but the automated tools don't make
  this easy

4. Follow editorconfig or other automatic formatters. If they conflict it is a bug and should be fixed.

<!-- TODO: figure out how to make sublists work. wow do I dislike markdown -->
