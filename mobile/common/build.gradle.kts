plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kmpNativeCoroutines)
}

kotlin {

    jvmToolchain(17)

    androidTarget()

//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach { iosTarget ->
//        iosTarget.binaries.framework {
//            baseName = "Shared"
//            isStatic = true
//        }
//    }
    
    sourceSets {
        commonMain.dependencies {
            api(project(":shared"))
            implementation(libs.bundles.ktor.client.common)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.koin.core)
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines.extensions)
            implementation(libs.logger.kermit)
            implementation(libs.jwtdecode)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.sqldelight.android.driver)
            implementation(libs.logging.interceptor)
            implementation(libs.androidx.datastore.preferences)
        }
//        iosMain.dependencies {
//            implementation(libs.ktor.client.darwin)
//            implementation(libs.sqldelight.native.driver)
//        }
    }
}

android {
    namespace = "com.zerogravity.moonlight.mobile.common"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}

sqldelight {
    databases {
        create("MoonlightMobileDb") {
            packageName.set("com.zerogravity.moonlight.mobile.common.data.local.db")
        }
    }
}

kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
}