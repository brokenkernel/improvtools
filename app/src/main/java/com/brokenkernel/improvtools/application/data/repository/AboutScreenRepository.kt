package com.brokenkernel.improvtools.application.data.repository

import android.content.pm.PackageInfo
import android.content.res.Resources

internal interface AboutScreenRepository {
    val packageInfo: PackageInfo
    val isSafeMode: Boolean
    val resources: Resources
}