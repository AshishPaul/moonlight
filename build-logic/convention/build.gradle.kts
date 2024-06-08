import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.zerogravity.moonlight.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.tools.build.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "moonlight.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidApplicationCompose") {
            id = "moonlight.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidApplicationNavigation") {
            id = "moonlight.android.application.navigation"
            implementationClass = "AndroidApplicationNavigationConventionPlugin"
        }

        register("androidApplicationUnitTest") {
            id = "moonlight.android.application.unitTest"
            implementationClass = "AndroidApplicationUnitTestConventionPlugin"
        }

        register("androidLibrary") {
            id = "moonlight.android.library"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "moonlight.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidLibraryNavigation") {
            id = "moonlight.android.library.navigation"
            implementationClass = "AndroidLibraryNavigationConventionPlugin"
        }

        register("androidLibraryUnitTest") {
            id = "moonlight.android.library.unitTest"
            implementationClass = "AndroidLibraryUnitTestConventionPlugin"
        }
        register("multiplatformCommonConventionPlugin") {
            id = "moonlight.multiplatform.common"
            implementationClass = "KotlinMultiplatformPlugin"
        }
    }
}