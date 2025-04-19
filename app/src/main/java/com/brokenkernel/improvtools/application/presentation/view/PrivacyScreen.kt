package com.brokenkernel.improvtools.application.presentation.view

import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import com.brokenkernel.improvtools.R

@Composable
internal fun PrivacyScreen() {
    val currentContext = LocalContext.current

    Column {
        Text(stringResource(R.string.privacy_notification_use))
        TextButton(
            onClick = {
                val url = "https://improvtools.brokenkernel.com/en/policy/privacy_policy/".toUri()
                val intent = CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .setUrlBarHidingEnabled(true)
                    .setBookmarksButtonEnabled(false)
                    .setDownloadButtonEnabled(false)
                    .setSendToExternalDefaultHandlerEnabled(true)
//                    .setCloseButton(BitmapFactory.decodeResource(getResources(), R.drawable.ic_back_arrow)) // TODO?
//                    .setEphemeralBrowsingEnabled(true)  TODO?
                    .build()

                // TODO: https://developer.chrome.com/docs/android/custom-tabs/guide-warmup-prefetch
                // TODO: https://developer.chrome.com/docs/android/custom-tabs/guide-partial-custom-tabs
                intent.launchUrl(currentContext, url)
            },
        ) {
            Text(stringResource(R.string.privacy_policy))
        }
    }
}
