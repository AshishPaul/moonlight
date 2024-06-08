import com.android.build.api.dsl.LibraryExtension
import com.zerogravity.moonlight.convention.internal.configureAndroid
import com.zerogravity.moonlight.convention.internal.configureKotlinMultiplatform
import com.zerogravity.moonlight.convention.internal.getPluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformPlugin: Plugin<Project> {

    override fun apply(target: Project):Unit = with(target){
        with(pluginManager){
            apply(getPluginId("kotlinMultiplatform"))
            apply(getPluginId("androidLibrary"))
            apply(getPluginId("kotlinSerialization"))
            apply(getPluginId("sqlDelight"))
            apply(getPluginId("ksp"))
            apply(getPluginId("kmpNativeCoroutines"))
        }

        configureKotlinMultiplatform(extensions.getByType(KotlinMultiplatformExtension::class.java))
        configureAndroid<LibraryExtension>()
    }
}