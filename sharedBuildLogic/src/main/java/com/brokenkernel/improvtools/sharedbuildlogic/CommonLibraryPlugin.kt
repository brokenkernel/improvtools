package com.brokenkernel.improvtools.sharedbuildlogic

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * For any android library (but not app)
 */
public class CommonLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure(
                LibraryExtension::class.java,
                {
                    compileSdk = 36

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

                    buildFeatures {
                        compose = true
                        buildConfig = true
                    }

                    compileOptions {
                        sourceCompatibility = JavaVersion.VERSION_21
                        targetCompatibility = JavaVersion.VERSION_21
                    }
                },
            )
        }
    }
}


