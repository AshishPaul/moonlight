rootProject.name = "moonlight"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":mobile:androidApp")
include(":mobile:common")
include(":shared")
include(":backend")

project(":shared").projectDir = File(rootDir, "moonlight-backend/shared")
project(":backend").projectDir = File(rootDir, "moonlight-backend/backend")