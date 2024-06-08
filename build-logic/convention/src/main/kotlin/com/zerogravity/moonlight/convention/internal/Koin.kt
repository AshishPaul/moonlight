package com.zerogravity.moonlight.convention.internal

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureKoinCore() {
    dependencies {
        add("implementation",getLibrary("koin-core"))
    }
}

internal fun Project.configureKoinAndroid() {
    dependencies {
        add("implementation",getLibrary("koin-android"))
        add("implementation",getLibrary("koin-androidx-compose"))
    }
}