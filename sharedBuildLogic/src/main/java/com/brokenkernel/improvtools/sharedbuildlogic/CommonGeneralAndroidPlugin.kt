package com.brokenkernel.improvtools.sharedbuildlogic

import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmDefaultMode
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

/**
 * For any non-android-library specific code.
 */
@OptIn(ExperimentalKotlinGradlePluginApi::class)
public class CommonGeneralAndroidPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(CommonKotlinPlugin::class.java)
            }


            extensions.configure(
                KotlinAndroidProjectExtension::class.java,
                {
                    version = libs.versions.kotlin.get()
                    compilerOptions {
                        languageVersion.set(KotlinVersion.KOTLIN_2_2)
                        apiVersion.set(KotlinVersion.KOTLIN_2_2)
//                        allWarningsAsErrors.set(true) // TODO!!
                        extraWarnings.set(true)
                        progressiveMode.set(true)
                        jvmDefault.set(JvmDefaultMode.NO_COMPATIBILITY)
                    }
                    jvmToolchain(21)
                },
            )

        }
    }
}


