---
title: "Code Structure"
---

The code is structured as follows. It isn't perfect (as there are not yet any tests) but should be generally followed.




res/
proto/
java/
ui/
ui/theme/

Each component is isolated from each other and has the following directories

```
/utils/

/data/
  should have no reference to Compose at all

/data/repository/
/data/model/
/data/serialisation/

/presentation/
/presentation/viewmodel/
/presentation/uistate/
/presentation/view/
```

The core application (in `/application/` is the only directory allowed to call into each of the other components.

```
application/
application/utils/
application/data/
application/data/repository/
application/data/model/
application/presentation/
application/presentation/view/
```


The base infra for the application is in `/infrastructure` and should have dependencies on nothing else.

The components for the application is in `/components` and should have dependencies on nothing else except infrastructure. These are composable typically.

TODO: make into chart
TODO: make into multi-module project so that `internal` actually helps.
