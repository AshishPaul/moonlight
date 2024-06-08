package com.zerogravity.moonlight.convention.internal

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKotlinMultiplatform(
    extension: KotlinMultiplatformExtension
) = extension.apply {
    jvmToolchain(17)

    // targets
    androidTarget()
//    iosArm64()
//    iosX64()
//    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()

    //common dependencies
    sourceSets.apply {
        commonMain.dependencies {
            api(project(":shared"))
            implementation(getBundle("ktor.client.common"))
            implementation(getLibrary("kotlinx.coroutines.core"))
            implementation(getLibrary("koin.core"))
            implementation(getLibrary("sqldelight.runtime"))
            implementation(getLibrary("sqldelight.coroutines.extensions"))
            implementation(getLibrary("logger.kermit"))
            implementation(getLibrary("jwtdecode"))
        }

        androidMain.dependencies {
            implementation(getLibrary("ktor.client.okhttp"))
            implementation(getLibrary("sqldelight.android.driver"))
            implementation(getLibrary("logging.interceptor"))
            implementation(getLibrary("androidx.datastore.preferences"))
        }
//        iosMain.dependencies {
//            implementation(getLibrary("ktor.client.darwin"))
//            implementation(getLibrary("sqldelight.native.driver"))
//        }
    }
    sourceSets.all {
        languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
    }
//
//    //applying the Cocoapods Configuration we made
//    (this as ExtensionAware).extensions.configure<CocoapodsExtension>(::configureKotlinCocoapods)
}