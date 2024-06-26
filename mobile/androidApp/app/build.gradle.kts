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
    implementation(project(":mobile:androidApp:core:ui"))
    implementation(project(":mobile:androidApp:features:home"))
    implementation(project(":mobile:androidApp:features:authentication"))
    implementation(project(":mobile:androidApp:features:category"))
    implementation(project(":mobile:androidApp:features:account"))
    implementation(libs.androidx.core.splash.screen)

}

