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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.presentation.viewmodel.AboutScreenViewModel

@Composable
internal fun AboutScreen(viewModel: AboutScreenViewModel = viewModel(factory = AboutScreenViewModel.Factory)) {
    val aboutScreenData by viewModel.uiState.collectAsState()

    // todo: null shouldn't be possible here, but need to figure out "default" packageInfo
    val packageInfo: PackageInfo? = aboutScreenData.packageInfo
    val versionName: String? = packageInfo?.versionName
    val packageName = packageInfo?.packageName
    val longVersionCode: Long = packageInfo?.longVersionCode ?: -1

    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    fun generateDebugInformationText(sectionHeaderStyle: SpanStyle, dataNameStyle: SpanStyle): AnnotatedString {
        fun buildHeaderRow(header: String, content: String): AnnotatedString {
            return buildAnnotatedString {
                // todo handle material theme
                withStyle(style = dataNameStyle) {
                    append(header)
                }
                append(" ")
                append(content)
            }
        }

        val result: AnnotatedString = buildAnnotatedString {
            // MaterialTheme.typography.titleLarge
            withStyle(style = sectionHeaderStyle) {
                appendLine(aboutScreenData.resources.getString(R.string.about_version_information))
                append("\n")
            }
            appendLine(
                buildHeaderRow(
                    aboutScreenData.resources.getString(R.string.about_package_name),
                    packageName ?: "(null)"
                )
            )
            append("\n")
            appendLine(
                buildHeaderRow(
                    aboutScreenData.resources.getString(R.string.about_version),
                    versionName ?: "(null)"
                )
            )
            append("\n")
            appendLine(
                buildHeaderRow(
                    aboutScreenData.resources.getString(R.string.about_long_version_code),
                    longVersionCode.toString()
                )
            )
            append("\n")
            // MaterialTheme.typography.titleLarge
            withStyle(style = sectionHeaderStyle) {
                appendLine(aboutScreenData.resources.getString(R.string.about_debug_information))
                append("\n")
            }
            appendLine(
                buildHeaderRow(
                    aboutScreenData.resources.getString(R.string.about_is_safe_mode),
                    aboutScreenData.isSafeMode.toString()
                )
            )
            append("\n")

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
//    Build.getRadioVersion("versionName")
//    Build.getMajorSdkVersion()
        }
        return result
    }

    fun copyAboutText(dataNameStyle: SpanStyle, sectionStyle: SpanStyle): Unit {
        clipboardManager.setText(
            generateDebugInformationText(
                sectionHeaderStyle = sectionStyle,
                dataNameStyle = dataNameStyle,
            )
        )
    }

    Column {
        Row {
            SelectionContainer {
                Text(
                    generateDebugInformationText(
                        sectionHeaderStyle = MaterialTheme.typography.displayMedium.toSpanStyle(),
                        dataNameStyle = MaterialTheme.typography.titleMedium.toSpanStyle(),
                    )
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            val sectionStyle = MaterialTheme.typography.titleLarge.toSpanStyle()
            val dataNameStyle = MaterialTheme.typography.displayMedium.toSpanStyle()
            FilledTonalButton(onClick = {
                copyAboutText(
                    sectionStyle = sectionStyle,
                    dataNameStyle = dataNameStyle,
                )
            }
            ) {
                Text(stringResource(R.string.about_copy_all))
            }
            ElevatedButton(
                onClick = {
                    // TODO copy all about info into an email
                },
                enabled = false, // TODO not yet functional
            ) {
                Text(stringResource(R.string.about_send_email))
            }
        }
    }
}