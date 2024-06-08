import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.zerogravity.moonlight.convention.internal.configureAndroid
import com.zerogravity.moonlight.convention.internal.configureCompose
import com.zerogravity.moonlight.convention.internal.configureNavigation
import com.zerogravity.moonlight.convention.internal.configureUnitTest
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.application")
        }
        configureCompose<ApplicationExtension>()
    }
}