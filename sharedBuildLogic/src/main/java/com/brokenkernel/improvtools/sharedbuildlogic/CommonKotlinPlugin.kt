package com.brokenkernel.improvtools.sharedbuildlogic

import com.autonomousapps.DependencyAnalysisPlugin
import com.github.benmanes.gradle.versions.VersionsPlugin
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.google.devtools.ksp.gradle.KspExtension
import com.squareup.sort.SortDependenciesPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.powerassert.gradle.PowerAssertGradleExtension
import org.jetbrains.kotlin.powerassert.gradle.PowerAssertGradlePlugin
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin

/**
 * For any non-android-library specific code.
 */
@OptIn(ExperimentalKotlinGradlePluginApi::class)
public class CommonKotlinPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(VersionsPlugin::class.java)
                apply(DependencyAnalysisPlugin::class.java)
                apply(SortDependenciesPlugin::class.java)
                apply(DokkaPlugin::class.java)
                apply(KtlintPlugin::class.java)
                apply(PowerAssertGradlePlugin::class.java)
            }

            tasks.withType<DependencyUpdatesTask>().configureEach {
                checkConstraints = true
                checkBuildEnvironmentConstraints = true
                checkForGradleUpdate = true
                rejectVersionIf {
                    when {
                        !isStable(currentVersion) -> false
                        !isStable(candidate.version) -> return@rejectVersionIf true
                        (
                            candidate.moduleIdentifier.equals("com.google.guava:guava") &&
                                candidate.version.endsWith("jre")
                            ) -> {
                            return@rejectVersionIf true
                        }

                        else -> return@rejectVersionIf false
                    }

                }
            }
            tasks.withType<DokkaTask>().configureEach {
                dokkaSourceSets.configureEach {
                    suppressGeneratedFiles.set(true)
                    reportUndocumented.set(true)
                }
            }
            extensions.configure(
                KspExtension::class.java,
            ) {
                useKsp2.set(true)
                allWarningsAsErrors = true
                arg("dagger.useBindingGraphFix", "enabled")
                arg("dagger.ignoreProvisionKeyWildcards", "enabled")
                arg("dagger.experimentalDaggerErrorMessages", "enabled")
                arg("dagger.warnIfInjectionFactoryNotGeneratedUpstream", "enabled")
                arg("dagger.fullBindingGraphValidation", "error")
            }
            extensions.configure(
                KtlintExtension::class.java,
                {
                    android.set(true)
                    coloredOutput.set(true)
                    version.set("1.6.0")
                },
            )

            extensions.configure(
                PowerAssertGradleExtension::class.java,
                {
                    functions.set(
                        listOf(
                            "kotlin.assert",
                            "kotlin.test.assertEquals",
                            "kotlin.test.assertTrue",
                            "kotlin.test.assertNull",
                            "kotlin.require",
                            "kotlin.util.assert",
                        ),
                    )
                },
            )
        }
    }
}

private fun isStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable
}

