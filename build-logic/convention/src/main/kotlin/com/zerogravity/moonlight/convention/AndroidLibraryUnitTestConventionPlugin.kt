import com.zerogravity.moonlight.convention.internal.configureUnitTest
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryUnitTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.library")
        }
        configureUnitTest()
    }
}