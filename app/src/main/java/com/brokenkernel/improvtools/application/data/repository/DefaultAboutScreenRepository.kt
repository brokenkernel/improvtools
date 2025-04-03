package com.brokenkernel.improvtools.application.data.repository

import android.content.pm.PackageInfo
import android.content.res.Resources

class DefaultAboutScreenRepository(
    override val packageInfo: PackageInfo,
    override val isSafeMode: Boolean,
    override val resources: Resources
) : AboutScreenRepository