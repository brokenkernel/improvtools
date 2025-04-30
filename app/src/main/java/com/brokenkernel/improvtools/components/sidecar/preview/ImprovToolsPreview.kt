package com.brokenkernel.improvtools.components.sidecar.preview

import androidx.compose.ui.tooling.preview.Preview

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
annotation class FontScalePreviews

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
annotation class LocalPreviews()

@FontScalePreviews
@LocalPreviews
@Preview(name = "Show Background", showBackground = true)
//@Preview(name = "Show System UI", showSystemUi = true)
@Preview
annotation class ImprovToolsAllPreviews()
