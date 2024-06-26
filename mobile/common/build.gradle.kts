plugins {
    alias(libs.plugins.moonlight.multiplatform.common)
}

android {
    namespace = "com.zerogravity.moonlight.mobile.common.android"

}

sqldelight {
    databases {
        create("MoonlightMobileDb") {
            packageName.set("com.zerogravity.moonlight.mobile.common.data.local.db")
        }
    }
}