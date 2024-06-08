package com.zerogravity.moonlight.convention.internal

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

internal inline fun <reified T : CommonExtension<*, *, *, *, *>> Project.configureCompose() {
    extensions.configure<T> {
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.9"
        }
    }

    dependencies {
        add("implementation", platform(getLibrary("androidx-compose-bom")))
        add("implementation", getLibrary("androidx-compose-activity"))
        add("implementation", getLibrary("androidx-compose-material"))
        add("implementation", getLibrary("androidx-compose-material3"))
        add("implementation", getLibrary("androidx-compose-material3-WindowSizeClass"))
        add("implementation", getLibrary("androidx-lifecycle-compose"))
        add("implementation", getLibrary("androidx-compose-ui-tooling-preview"))
        add("debugImplementation", getLibrary("androidx-compose-ui-tooling"))
    }
}