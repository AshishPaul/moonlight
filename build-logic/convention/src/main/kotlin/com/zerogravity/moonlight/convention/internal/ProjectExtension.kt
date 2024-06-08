package com.zerogravity.moonlight.convention.internal

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.getLibrary(name: String) = libs.findLibrary(name).get()

internal fun Project.getPluginId(name: String) = libs.findPlugin(name).get().get().pluginId

internal fun Project.getBundle(name: String) = libs.findBundle(name).get()