package com.brokenkernel.improvtools.sharedbuildlogic

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

public class CommonLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
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

                    defaultConfig {
                        minSdk = 26
                        consumerProguardFiles("consumer-rules.pro")
                    }
                },
            )
        }
    }
}


