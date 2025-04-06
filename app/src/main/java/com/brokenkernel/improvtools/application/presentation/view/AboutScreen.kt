package com.brokenkernel.improvtools.application.presentation.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager.PackageInfoFlags
import android.os.Build
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.core.net.toUri
import com.brokenkernel.improvtools.BuildConfig
import com.brokenkernel.improvtools.R
import kotlinx.coroutines.launch

@Composable
internal fun AboutScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    val crScope = rememberCoroutineScope()
    val resources = LocalContext.current.resources
    val packageManager = LocalContext.current.packageManager


    // TODO: figure out how to do content generation off of UI. Maybe bring back viewmodel?
    val packageInfo: PackageInfo =
        packageManager.getPackageInfo(LocalContext.current.packageName, PackageInfoFlags.of(0))
    val versionName: String? = packageInfo.versionName
    val packageName = packageInfo.packageName
    val longVersionCode: Long = packageInfo.longVersionCode

    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    fun generateDebugInformationText(): String {
        // TODO: possibly directly handle HTML instead of relying on annotated string
        val result: String = """
            |<big>${resources.getString(R.string.about_version_information)}</big>
            |${
            resources.getString(
                R.string.about_buildconfig_version_code,
                BuildConfig.VERSION_CODE.toString()
            )
        }
            |${resources.getString(R.string.about_long_version_code, longVersionCode.toString())}
            |${resources.getString(R.string.about_package_name, packageName)}
            |${resources.getString(R.string.about_buildconfig_version, BuildConfig.VERSION_NAME)}
            |${resources.getString(R.string.about_version, versionName ?: "(null)")}
            |${resources.getString(R.string.about_buildconfig_buildtype, BuildConfig.BUILD_TYPE)}
            |<big>${resources.getString(R.string.about_debug_information)}</big>
            |${
            resources.getString(
                R.string.about_buildconfig_enable_strict_death,
                BuildConfig.ENABLE_STRICT_MODE_DEATH
            )
        }
            |${
            resources.getString(
                R.string.about_buildconfig_enable_crashylitics,
                BuildConfig.ENABLE_CRASHLYTICS
            )
        }
            |${resources.getString(R.string.about_is_safe_mode, packageManager.isSafeMode)}
            |
            |BOARD=${Build.BOARD}
            |BOOTLOADER=${Build.BOOTLOADER}
            |BRAND=${Build.BRAND}
            |DEVICE=${Build.DEVICE}
            |DISPLAY=${Build.DISPLAY}
            |FINGERPRINT=${Build.FINGERPRINT}
            |HARDWARE=${Build.HARDWARE}
            |HOST=${Build.HOST}
            |ID=${Build.ID}
            |MANUFACTURER=${Build.MANUFACTURER}
            |MODEL=${Build.MODEL}
            |ODM_SKU=${Build.ODM_SKU}
            |PRODUCT=${Build.PRODUCT}
            |SKU=${Build.SKU}
            |SOC_MANUFACTURER=${Build.SOC_MANUFACTURER}
            |SOC_MODEL=${Build.SOC_MODEL}
            |SUPPORTED_ABIS=${Build.SUPPORTED_ABIS.contentToString()}
            |TAGS=${Build.TAGS}
            |TIME=${Build.TIME}
            |TYPE=${Build.TYPE}
            |USER=${Build.USER}
        """.trimMargin().replace("\n", "<br/>")

        // further debug information for the future
//    Build.getFingerprintedPartitions()
//    Build.getRadioVersion("versionName")
//    Build.getMajorSdkVersion()
        return result
    }

    fun copyAboutText(): Unit {
        clipboardManager.setText(
            AnnotatedString.fromHtml(generateDebugInformationText())
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxHeight().weight(100f)) {
            SelectionContainer {
                Text(
                    AnnotatedString.fromHtml(generateDebugInformationText())
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult(),
                onResult = {},
            )
            FilledTonalButton(onClick = {
                copyAboutText(
                )
            }
            ) {
                Text(stringResource(R.string.about_copy_all))
            }
            ElevatedButton(
                onClick = {
                    val textToBeEmailed = generateDebugInformationText(
                    )

                    val intent = Intent(Intent.ACTION_SENDTO, "mailto:".toUri())
                        .putExtra(
                            Intent.EXTRA_EMAIL,
                            arrayOf(resources.getString(R.string.about_contact_email_address))
                        )
                        .putExtra(
                            Intent.EXTRA_SUBJECT,
                            resources.getString(R.string.about_improvtools_feature_request)
                        )
                        .putExtra(
                            Intent.EXTRA_TEXT,
                            Html.fromHtml(textToBeEmailed.toString(), FROM_HTML_MODE_COMPACT)
                        )
                    try {
                        launcher.launch(intent)
                    } catch (_: ActivityNotFoundException) {
                        crScope.launch {
                            snackbarHostState.showSnackbar(
                                resources.getString(R.string.error_no_email_application_available)
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