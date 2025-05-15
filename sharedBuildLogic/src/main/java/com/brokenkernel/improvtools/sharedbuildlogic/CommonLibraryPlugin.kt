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
        }
    }
}


