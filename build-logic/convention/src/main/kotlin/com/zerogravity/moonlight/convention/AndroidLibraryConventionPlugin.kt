import com.android.build.gradle.LibraryExtension
import com.zerogravity.moonlight.convention.internal.configureAndroid
import com.zerogravity.moonlight.convention.internal.configureKapt
import com.zerogravity.moonlight.convention.internal.configureKoinAndroid
import com.zerogravity.moonlight.convention.internal.configureKoinCore
import com.zerogravity.moonlight.convention.internal.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
            apply("org.jetbrains.kotlin.kapt")
        }

        configureAndroid<LibraryExtension>()
        configureKotlin<LibraryExtension>()
        configureKapt()
        configureKoinCore()
    }
}