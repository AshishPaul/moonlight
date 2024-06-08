package com.zerogravity.moonlight.convention.internal

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureNavigation() {
    dependencies {
        add("implementation",getLibrary("androidx-navigation-compose"))
    }
}