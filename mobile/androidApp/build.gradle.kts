plugins {
    alias(libs.plugins.moonlight.android.application)
    alias(libs.plugins.moonlight.android.application.compose)
    alias(libs.plugins.moonlight.android.application.navigation)
}

android {
    namespace = "com.zerogravity.moonlight.mobile.android"

}

dependencies {
    implementation(projects.mobile.common)

    implementation(libs.androidx.credentials)
    // optional - needed for credentials support from play services, for devices running
    // Android 13 and below.
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.androidx.core.splash.screen)

}

