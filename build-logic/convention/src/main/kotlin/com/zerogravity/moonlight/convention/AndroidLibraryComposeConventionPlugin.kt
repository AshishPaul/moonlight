import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.zerogravity.moonlight.convention.internal.configureCompose
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.library")
        }
        configureCompose<LibraryExtension>()
    }
}