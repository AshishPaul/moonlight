plugins {
    alias(libs.plugins.moonlight.android.library)
    alias(libs.plugins.moonlight.android.library.compose)
    alias(libs.plugins.moonlight.android.library.navigation)
}

android {
    namespace = "com.zerogravity.moonlight.mobile.features.account"

}

dependencies {
    implementation(projects.mobile.common)
    implementation(project(":mobile:androidApp:core:ui"))

}

