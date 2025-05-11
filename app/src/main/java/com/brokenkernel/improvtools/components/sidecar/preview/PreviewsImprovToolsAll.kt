package com.brokenkernel.improvtools.components.sidecar.preview

import androidx.compose.ui.tooling.preview.Preview

@MustBeDocumented
@Preview(
    name = "small font",
    group = "font scales",
    fontScale = 0.5f,
)
@Preview(
    name = "standard font",
    group = "font scales",
    fontScale = 1.0f,
)
@Preview(
    name = "large font",
    group = "font scales",
    fontScale = 1.5f,
)
annotation class PreviewsFontScale

@MustBeDocumented
@Preview(
    name = "english",
    group = "locale",
    locale = "en-US",
)
@Preview(
    name = "english",
    group = "locale",
    locale = "en-GB",
)
@Preview(
    name = "english",
    group = "locale",
    locale = "de-DE",
)
annotation class PreviewsLocales()

@MustBeDocumented
@PreviewsFontScale
@PreviewsLocales
@Preview(name = "Show Background", showBackground = true)
// @Preview(name = "Show System UI", showSystemUi = true)
@Preview
annotation class PreviewsImprovToolsAll()
