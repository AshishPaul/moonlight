import com.zerogravity.moonlight.convention.internal.configureNavigation
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidApplicationNavigationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.application")
        }
        configureNavigation()
    }
}