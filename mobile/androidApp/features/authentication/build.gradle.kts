plugins {
    alias(libs.plugins.moonlight.android.library)
    alias(libs.plugins.moonlight.android.library.compose)
    alias(libs.plugins.moonlight.android.library.navigation)
}

android {
    namespace = "com.zerogravity.moonlight.mobile.features.authentication"

}

dependencies {
    implementation(projects.mobile.common)
    implementation(project(":mobile:androidApp:core:ui"))

    implementation(libs.androidx.credentials)
    // optional - needed for credentials support from play services, for devices running
    // Android 13 and below.
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
}

