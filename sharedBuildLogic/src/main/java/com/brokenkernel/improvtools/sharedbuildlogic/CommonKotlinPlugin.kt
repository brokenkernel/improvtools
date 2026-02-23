package com.brokenkernel.improvtools.sharedbuildlogic

//import org.jetbrains.kotlin.gradle.dsl.JvmDefaultMode
//import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.powerassert.gradle.PowerAssertGradleExtension

/**
 * For any non-android-library specific code.
 */
@OptIn(ExperimentalKotlinGradlePluginApi::class)
public class CommonKotlinPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.dokka")
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

//            extensions.configure(DokkaExtension::class.java) { dokka ->
//            }
//            tasks.withType<DokkaTask>().configureEach {
//                dokkaSourceSets.configureEach {
//                    suppressGeneratedFiles.set(true)
//                    reportUndocumented.set(true)
//                }
//            }

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

//            dokka {
//                version = libs.versions.kotlin.get()
//                compilerOptions {
//                    languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_2)
//                    apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_2)
//                    progressiveMode.set(true)
////                    allWarningsAsErrors.set(true) // TODO
//                    jvmDefault.set(JvmDefaultMode.NO_COMPATIBILITY)
//                }
//            }
//        }


            extensions.configure(JavaPluginExtension::class.java) {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21

            }
        }
    }
}

private fun isStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(?:-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable
}

