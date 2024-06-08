import com.android.build.api.dsl.LibraryExtension
import com.zerogravity.moonlight.convention.internal.configureAndroid
import com.zerogravity.moonlight.convention.internal.configureCompose
import com.zerogravity.moonlight.convention.internal.configureNavigation
import com.zerogravity.moonlight.convention.internal.configureUnitTest
import org.gradle.api.Plugin
import org.gradle.api.Project

class FeatureModuleConventions : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.library")
        }

        configureAndroid<LibraryExtension>()
        configureUnitTest()
        configureCompose<LibraryExtension>()
        configureNavigation()
    }
}