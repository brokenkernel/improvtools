package com.brokenkernel.improvtools.application.presentation.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageInfo
import android.os.Build
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.brokenkernel.improvtools.BuildConfig
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.presentation.viewmodel.AboutScreenViewModel
import kotlinx.coroutines.launch

@Composable
internal fun AboutScreen(viewModel: AboutScreenViewModel = hiltViewModel()) {
    val aboutScreenData by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val crScope = rememberCoroutineScope()

    // todo: null shouldn't be possible here, but need to figure out "default" packageInfo
    val packageInfo: PackageInfo? = aboutScreenData.packageInfo
    val versionName: String? = packageInfo?.versionName
    val packageName = packageInfo?.packageName
    val longVersionCode: Long = packageInfo?.longVersionCode ?: -1

    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    fun generateDebugInformationText(sectionHeaderStyle: SpanStyle, dataNameStyle: SpanStyle): AnnotatedString {
        fun buildHeaderRow(header: String, content: String): AnnotatedString {
            return buildAnnotatedString {
                withStyle(style = dataNameStyle) {
                    append(header)
                }
                append(" ")
                append(content)
            }
        }

        val result: AnnotatedString = buildAnnotatedString {
            withStyle(style = sectionHeaderStyle) {
                appendLine(aboutScreenData.resources.getString(R.string.about_version_information))
            }
            appendLine(
                buildHeaderRow(
                    aboutScreenData.resources.getString(R.string.about_package_name),
                    packageName ?: "(null)"
                )
            )
            appendLine(
                buildHeaderRow(
                    aboutScreenData.resources.getString(R.string.about_buildconfig_version),
                    BuildConfig.VERSION_NAME
                )
            )
            appendLine(
                buildHeaderRow(
                    aboutScreenData.resources.getString(R.string.about_version),
                    versionName ?: "(null)"
                )
            )
            appendLine(
                buildHeaderRow(
                    aboutScreenData.resources.getString(R.string.about_buildconfig_buildtype),
                    BuildConfig.BUILD_TYPE,
                )
            )
            appendLine(
                buildHeaderRow(
                    aboutScreenData.resources.getString(R.string.about_buildconfig_version_code),
                    BuildConfig.VERSION_CODE.toString()
                )
            )
            appendLine(
                buildHeaderRow(
                    aboutScreenData.resources.getString(R.string.about_long_version_code),
                    longVersionCode.toString()
                )
            )
            append("\n")
            withStyle(style = sectionHeaderStyle) {
                appendLine(
                        aboutScreenData.resources.getString(R.string.about_debug_information)

                )
            }
            appendLine(
                Html.fromHtml(
                    aboutScreenData.resources.getString(
                        R.string.about_buildconfig_enable_strict_death,
                        BuildConfig.ENABLE_STRICT_MODE_DEATH
                    ),
                    FROM_HTML_MODE_COMPACT
                )

            )
            appendLine(
                Html.fromHtml(
                    aboutScreenData.resources.getString(
                        R.string.about_buildconfig_enable_crashylitics,
                        BuildConfig.ENABLE_CRASHLYTICS
                    ),
                    FROM_HTML_MODE_COMPACT
                )
            )

            appendLine(
                buildHeaderRow(
                    aboutScreenData.resources.getString(R.string.about_is_safe_mode),
                    aboutScreenData.isSafeMode.toString()
                )
            )

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

    fun copyAboutText(dataNameStyle: SpanStyle, sectionHeaderStyle: SpanStyle): Unit {
        clipboardManager.setText(
            generateDebugInformationText(
                sectionHeaderStyle = sectionHeaderStyle,
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
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult(),
                onResult = {},
            )
            FilledTonalButton(onClick = {
                copyAboutText(
                    sectionHeaderStyle = sectionStyle,
                    dataNameStyle = dataNameStyle,
                )
            }
            ) {
                Text(stringResource(R.string.about_copy_all))
            }
            ElevatedButton(
                onClick = {
                    val textToBeEmailed = generateDebugInformationText(
                        sectionHeaderStyle = sectionStyle,
                        dataNameStyle = dataNameStyle,
                    )

                    val intent = Intent(Intent.ACTION_SENDTO, "mailto:".toUri())
                        .putExtra(
                            Intent.EXTRA_EMAIL,
                            arrayOf(aboutScreenData.resources.getString(R.string.about_contact_email_address))
                        )
                        .putExtra(
                            Intent.EXTRA_SUBJECT,
                            aboutScreenData.resources.getString(R.string.about_improvtools_feature_request)
                        )
                        .putExtra(
                            Intent.EXTRA_TEXT,
                            Html.fromHtml(textToBeEmailed.toString(), Html.FROM_HTML_MODE_COMPACT)
                        )
                    try {
                        launcher.launch(intent)
                    } catch (_: ActivityNotFoundException) {
                        crScope.launch {
                            snackbarHostState.showSnackbar(
                                aboutScreenData.resources.getString(R.string.error_no_email_application_available)
                            )
                        }
                    }
                },
            ) {
                Text(stringResource(R.string.about_send_email))
            }
        }
    }
}