package com.brokenkernel.improvtools.sidecar.customtabs

import android.content.Context
import android.util.Log
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.ui.platform.UriHandler
import androidx.core.net.toUri
import com.brokenkernel.improvtools.TAG

class CustomTabUriHandler(val context: Context) : UriHandler {
    private val customTabSessionManager = CustomTabSessionManager()

    init {
        customTabSessionManager.bindCustomTabService(context)
    }

    override fun openUri(uri: String) {
        if (Log.isLoggable(TAG, Log.INFO)) {
            Log.i(TAG, "openUri: $uri")
        }
        val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder(customTabSessionManager.mSession)
        val customTabsIntent: CustomTabsIntent = builder.build()
        val customTabPackage = CustomTabsClient.getPackageName(context, null)
        customTabsIntent.intent.setPackage(customTabPackage)
        customTabsIntent.launchUrl(context, uri.toUri())
    }
}
