package com.brokenkernel.improvtools.sharedbuildlogic

import com.android.build.gradle.LibraryExtension
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
import org.jetbrains.kotlin.gradle.dsl.JvmDefaultMode
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin
import org.jlleitschuh.gradle.ktlint.tasks.KtLintCheckTask

public class CommonLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(VersionsPlugin::class.java)
                apply(DependencyAnalysisPlugin::class.java)
                apply(SortDependenciesPlugin::class.java)
                apply(DokkaPlugin::class.java)
                apply(KtlintPlugin::class.java)
            }

            tasks.withType<DependencyUpdatesTask>().configureEach {
                checkConstraints = true
                checkBuildEnvironmentConstraints = true
                checkForGradleUpdate = true
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
                    version.set("1.5.0")
                },
            )
            extensions.configure(
                LibraryExtension::class.java,
                {
                    compileSdk = 36
                    buildToolsVersion = "35.0.0"

                    lint {
                        lintConfig = file("lint.xml")
                        baseline = file("lint-baseline.xml")
                        checkDependencies = true
                        warningsAsErrors = true
                    }
                }
            )

            extensions.configure(
                KotlinAndroidProjectExtension::class.java,
                {
                    version = "2.2.0"
                    compilerOptions {
                        languageVersion.set(KotlinVersion.KOTLIN_2_2)
                        apiVersion.set(KotlinVersion.KOTLIN_2_2)

                        allWarningsAsErrors.set(true)
                        extraWarnings.set(true)
                        progressiveMode.set(true)
                        jvmDefault.set(JvmDefaultMode.NO_COMPATIBILITY)
                    }
                    jvmToolchain(21)
                }
            )
        }
    }
}


