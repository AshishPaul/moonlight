plugins {
    alias(libs.plugins.moonlight.multiplatform.common)
}
sqldelight {
    databases {
        create("MoonlightMobileDb") {
            packageName.set("com.zerogravity.moonlight.mobile.common.data.local.db")
        }
    }
}