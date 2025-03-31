package com.brokenkernel.improvtools.application.presentation.view

import android.content.pm.PackageInfo
import android.content.pm.PackageManager.PackageInfoFlags
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.brokenkernel.improvtools.R

@Composable
fun AboutScreen() {
    val context = LocalContext.current
    val packageManager = context.packageManager
    val packageInfo: PackageInfo = packageManager.getPackageInfo(context.packageName, PackageInfoFlags.of(0))
    val versionName: String? = packageInfo.versionName
    val packageName = packageInfo.packageName
    val longVersionCode: Long = packageInfo.longVersionCode
    val isSafeMode = packageManager.isSafeMode



    Column {
        Row {
            Text(
                stringResource(R.string.about_version_information),
                style = MaterialTheme.typography.titleLarge)
        }
        Row {
            Text(stringResource(R.string.about_package_name))
            Text(packageName)
        }
        Row {
            Text(stringResource(R.string.about_version))
            Text(versionName.orEmpty())
        }
        Row {
            Text(stringResource(R.string.about_long_version_code))
            Text(longVersionCode.toString())
        }
        Row {
            Text(
                stringResource(R.string.about_debug_information),
                style = MaterialTheme.typography.titleLarge)
        }
        Row {
            Text(stringResource(R.string.about_is_safe_mode))
            Text(isSafeMode.toString())
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            FilledTonalButton(onClick = {
                    // TODO copy all about info into paste
                },
                enabled = false, // TODO not yet functional
            ) {
                Text(stringResource(R.string.about_copy_all))
            }
            ElevatedButton(onClick = {
                    // TODO copy all about info into an email
                },
                enabled = false, // TODO not yet functional
            ) {
                Text(stringResource(R.string.about_send_email))
            }
        }
    }

}