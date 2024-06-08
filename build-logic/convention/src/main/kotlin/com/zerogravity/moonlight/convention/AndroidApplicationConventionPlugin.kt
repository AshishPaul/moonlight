import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.zerogravity.moonlight.convention.internal.configureAndroid
import com.zerogravity.moonlight.convention.internal.configureKapt
import com.zerogravity.moonlight.convention.internal.configureKoinAndroid
import com.zerogravity.moonlight.convention.internal.configureKoinCore
import com.zerogravity.moonlight.convention.internal.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
            apply("org.jetbrains.kotlin.kapt")
        }

        extensions.configure<BaseAppModuleExtension> {
            defaultConfig {
                applicationId = "com.zerogravity.moonlight.mobile.android"
                targetSdk = 34
                versionCode = 1
                versionName = "1.0"

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                vectorDrawables {
                    useSupportLibrary = true
                }
            }
            buildTypes {
                release {
                    isMinifyEnabled = true
                    isShrinkResources = true
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }
            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }
        }
        configureAndroid<BaseAppModuleExtension>()
        configureKotlin<BaseAppModuleExtension>()
        configureKapt()
        configureKoinCore()
        configureKoinAndroid()
    }
}