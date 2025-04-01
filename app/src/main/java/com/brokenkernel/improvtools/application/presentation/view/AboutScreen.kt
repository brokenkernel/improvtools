package com.brokenkernel.improvtools.application.presentation.view

import android.content.pm.PackageInfo
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.presentation.viewmodel.AboutScreenViewModel

@Composable
internal fun AboutScreen(viewModel: AboutScreenViewModel = viewModel(factory = AboutScreenViewModel.Factory)) {
    val aboutScreenScreen by viewModel.uiState.collectAsState()

    // todo: null shouldn't be possible here, but need to figure out "default" packageInfo
    val packageInfo: PackageInfo? = aboutScreenScreen.packageInfo
    val versionName: String? = packageInfo?.versionName
    val packageName = packageInfo?.packageName
    val longVersionCode: Long = packageInfo?.longVersionCode ?: -1

    // further debug information for the future
    Build.BOARD
    Build.BOOTLOADER
    Build.BRAND
    Build.DEVICE
    Build.DISPLAY
    Build.FINGERPRINT
    Build.HARDWARE
    Build.HOST
    Build.ID
    Build.MANUFACTURER
    Build.MODEL
    Build.ODM_SKU
    Build.PRODUCT
    Build.SKU
    Build.SOC_MANUFACTURER
    Build.SOC_MODEL
    Build.SUPPORTED_ABIS
    Build.TAGS
    Build.TIME
    Build.TYPE
    Build.USER
//    Build.getFingerprintedPartitions()
    Build.getRadioVersion()
//    Build.getMajorSdkVersion()



    Column {
        Row {
            SelectionContainer {
                Text(
                    stringResource(R.string.about_version_information),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        Row {
            SelectionContainer {
                Text(stringResource(R.string.about_package_name))
            }
            SelectionContainer {
                Text(packageName?: "(null)")
            }
        }
        Row {
            SelectionContainer {
                Text(stringResource(R.string.about_version))
            }
            SelectionContainer {
                Text(versionName.orEmpty())
            }
        }
        Row {
            SelectionContainer {
                Text(stringResource(R.string.about_long_version_code))
            }
            SelectionContainer {
                Text(longVersionCode.toString())
            }
        }
        Row {
            SelectionContainer {
                Text(
                    stringResource(R.string.about_debug_information),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        Row {
            SelectionContainer {
                Text(stringResource(R.string.about_is_safe_mode))
            }
            SelectionContainer {
                Text(aboutScreenScreen.isSafeMode.toString())
            }
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