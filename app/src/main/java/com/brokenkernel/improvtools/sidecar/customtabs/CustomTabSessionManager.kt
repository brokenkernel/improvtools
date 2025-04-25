package com.brokenkernel.improvtools.sidecar.customtabs

import android.content.ComponentName
import android.content.Context
import androidx.browser.customtabs.CustomTabsCallback
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsServiceConnection
import androidx.browser.customtabs.CustomTabsSession

internal class CustomTabSessionManager {

    private var mClient: CustomTabsClient? = null
    var mSession: CustomTabsSession? = null

    private val mConnection: CustomTabsServiceConnection = object : CustomTabsServiceConnection() {
        override fun onCustomTabsServiceConnected(
            name: ComponentName,
            client: CustomTabsClient,
        ) {
            client.warmup(0)
            mSession = client.newSession(CustomTabsCallback())
            mClient = client
        }

        override fun onServiceDisconnected(name: ComponentName) {
            mClient = null
            mSession = null
        }
    }

    fun bindCustomTabService(context: Context) {
        if (mClient != null) {
            return
        }

        val packageName = CustomTabsClient.getPackageName(context, null)
        if (packageName == null) {
            return
        }

        CustomTabsClient.bindCustomTabsService(context, packageName, mConnection)
    }
}
