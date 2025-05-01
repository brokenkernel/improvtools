package com.brokenkernel.improvtools.application.presentation.view

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager.PackageInfoFlags
import android.os.Build
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.core.net.toUri
import com.brokenkernel.components.view.HtmlText
import com.brokenkernel.improvtools.BuildConfig
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.utils.AboutTabViewModel
import com.brokenkernel.improvtools.components.presentation.view.ExpandIcon
import kotlinx.coroutines.launch

@Composable
internal fun AboutTab(
    viewModel: AboutTabViewModel = AboutTabViewModel(),
    onLaunchCallback: () -> Unit,
    onNavigateToPrivacyScreen: () -> Unit,
) {
    // TODO: consider making a BaseScreenComposable or some such
    LaunchedEffect(Unit) {
        onLaunchCallback()
    }
    // move snackbar host state into app state. And then inject it?
    // also include more injected stuff (settings for ex) into debug datum
    val snackbarHostState = LocalSnackbarHostState.current
    val crScope = rememberCoroutineScope()
    val resources = LocalContext.current.resources
    val packageManager = LocalContext.current.packageManager

    // TODO: figure out how to do content generation off of UI. Maybe bring back viewmodel?
    val packageInfo: PackageInfo? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getPackageInfo(LocalContext.current.packageName, PackageInfoFlags.of(0))
        } else {
            null
        }
    val versionName: String? = packageInfo?.versionName
    val packageName = packageInfo?.packageName
    val longVersionCode: Long? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        packageInfo?.longVersionCode
    } else {
        null
    }
    val localConfig = LocalConfiguration.current

    val context = LocalContext.current
    val clipboard: Clipboard = LocalClipboard.current

    fun generateGeneralInformationText(): String {
        val result: String = """
        |<big>${resources.getString(R.string.about_contact_info)}</big>
        |🌐 <u><a href="https://github.com/brokenkernel/improvtools">${
            resources.getString(
                R.string.about_contact_code_repo,
            )
        }</a></u>
        """.trimMargin().replace("\n", "<br/>")
        return result
    }

    fun generateDebugInformationText(): String {
        val basicResult: String = """
            |<big>${resources.getString(R.string.about_version_information)}</big>
            |${
            resources.getString(
                R.string.about_buildconfig_version_code,
                BuildConfig.VERSION_CODE,
            )
        }
            |${resources.getString(R.string.about_long_version_code, longVersionCode)}
            |${resources.getString(R.string.about_package_name, packageName)}
            |${resources.getString(R.string.about_buildconfig_version, BuildConfig.VERSION_NAME)}
            |${resources.getString(R.string.about_version, versionName ?: "(null)")}
            |${resources.getString(R.string.about_buildconfig_buildtype, BuildConfig.BUILD_TYPE)}
            |<big>${resources.getString(R.string.about_debug_information)}</big>
            |${
            resources.getString(
                R.string.about_buildconfig_enable_strict_death,
                BuildConfig.ENABLE_STRICT_MODE_DEATH,
            )
        }
            |${
            resources.getString(
                R.string.about_buildconfig_enable_crashylitics,
                BuildConfig.ENABLE_CRASHLYTICS,
            )
        }
            |${resources.getString(R.string.about_is_safe_mode, packageManager.isSafeMode)}
            |
            |<big>Build Constants</big>
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
            |PRODUCT=${Build.PRODUCT}
            |SUPPORTED_ABIS=${Build.SUPPORTED_ABIS.contentToString()}
            |TAGS=${Build.TAGS}
            |TIME=${Build.TIME}
            |TYPE=${Build.TYPE}
            |USER=${Build.USER}
            |
            |<big>LocalConfig</big>
            |Locales=${localConfig.locales}
            |NavigationType=${localConfig.navigation}
            |ColorMode=${localConfig.colorMode}
            |DensityDPI=${localConfig.densityDpi}
            |NavigationHidden=${localConfig.navigationHidden}
            |Orientation=${localConfig.orientation}
            |ScreenLayout=${localConfig.screenLayout}
            |ScreenHDR=${localConfig.isScreenHdr}
            |WideColorGamut=${localConfig.isScreenWideColorGamut}
            |ScreenRound=${localConfig.isScreenRound}
            |uiMode=${localConfig.uiMode}
            |Touchscreen=${localConfig.touchscreen}
            |smallestScreenWidthDp=${localConfig.smallestScreenWidthDp}
            |layoutDirection=${localConfig.layoutDirection}
            |layoutDirection=${localConfig.layoutDirection}
        """.trimMargin().replace("\n", "<br/>")

        // Technically this skips some information for 33,34, but not that important.
        val additionalDataForSDK35Plus = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            """
                |<big> Additional Build Datum </big>
                |ODM_SKU=${Build.ODM_SKU}
                |SKU=${Build.SKU}
                |SOC_MANUFACTURER=${Build.SOC_MANUFACTURER}
                |SOC_MODEL=${Build.SOC_MODEL}
                |
                |<big>Additional LocalConfig Datum</big>
                |NightMode=${localConfig.isNightModeActive}
                |grammaticalGender=${localConfig.grammaticalGender}
            """.trimMargin().replace("\n", "<br/>")
        } else {
            ""
        }

        val result = basicResult + additionalDataForSDK35Plus

        // further debug information for the future
//    Build.getFingerprintedPartitions()
//    Build.getRadioVersion("versionName")
//    Build.getMajorSdkVersion()
        return result
    }

    fun copyAboutText() {
        val wordString = AnnotatedString.fromHtml(generateDebugInformationText())
        val clipData = ClipData.newHtmlText(
            context.getString(R.string.about_debugging_data),
            wordString.text,
            wordString.toString(),
        )
        val clipEntry = clipData.toClipEntry()

        crScope.launch {
            clipboard.setClipEntry(clipEntry)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxHeight()
                .weight(100f),
        ) {
            SelectionContainer {
                Column {
                    Text(
                        AnnotatedString.fromHtml(generateGeneralInformationText()),
                    )
                    HtmlText(
                        resources.getString(
                            R.string.about_buildconfig_version_code,
                            BuildConfig.VERSION_CODE,
                        ),
                    )
                    var isDebugDataExpanded by rememberSaveable { mutableStateOf(false) }
                    // TODO: make utility collapsible card
                    Card(
                        modifier = Modifier
                            .clickable(
                                onClick = { isDebugDataExpanded = !isDebugDataExpanded },
                                onClickLabel = stringResource(R.string.component_collapse_card),
                                enabled = true,
                                role = Role.Switch,
                            ),
                    ) {
                        Row {
                            if (isDebugDataExpanded) {
                                Text(
                                    AnnotatedString.fromHtml(generateDebugInformationText()),
                                )
                            } else {
                                Text(
                                    stringResource(R.string.about_show_debug_data),
                                )
                                // not shown when expanded, but that's okay.
                                ExpandIcon(isDebugDataExpanded)
                            }
                        }
                    }
                    TextButton(
                        onClick = onNavigateToPrivacyScreen,
                    ) {
                        Text(stringResource(R.string.about_show_privacy_screen))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
        ) {
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult(),
                onResult = {},
            )
            FilledTonalButton(
                onClick = {
                    copyAboutText()
                },
            ) {
                Text(stringResource(R.string.about_copy_all))
            }
            ElevatedButton(
                onClick = {
                    val textToBeEmailed = generateDebugInformationText()

                    val intent = Intent(Intent.ACTION_SENDTO, "mailto:".toUri())
                        .putExtra(
                            Intent.EXTRA_EMAIL,
                            arrayOf(resources.getString(R.string.about_contact_email_address)),
                        )
                        .putExtra(
                            Intent.EXTRA_SUBJECT,
                            resources.getString(R.string.about_improvtools_feature_request),
                        )
                        .putExtra(
                            Intent.EXTRA_TEXT,
                            Html.fromHtml(textToBeEmailed, FROM_HTML_MODE_COMPACT),
                        )
                    try {
                        launcher.launch(intent)
                    } catch (_: ActivityNotFoundException) {
                        crScope.launch {
                            snackbarHostState.showSnackbar(
                                resources.getString(R.string.error_no_email_application_available),
                                withDismissAction = true,
                                duration = SnackbarDuration.Short,
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

// TODO: add     Log.v(TAG, "verbose: " + Log.isLoggable(TAG, Log.VERBOSE));
