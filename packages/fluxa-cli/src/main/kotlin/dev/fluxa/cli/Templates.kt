package dev.fluxa.cli

object Templates {

    fun settingsGradle(appName: String) = """
        |rootProject.name = "$appName"
        |
        |pluginManagement {
        |    repositories {
        |        gradlePluginPortal()
        |        mavenCentral()
        |        google()
        |    }
        |}
        |
        |dependencyResolutionManagement {
        |    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        |    repositories {
        |        mavenCentral()
        |        google()
        |    }
        |}
        |
        |include(":app")
    """.trimMargin() + "\n"

    fun rootBuildGradle() = """
        |plugins {
        |    id("com.android.application") version "9.1.0" apply false
        |    id("org.jetbrains.kotlin.plugin.compose") version "2.3.10" apply false
        |}
    """.trimMargin() + "\n"

    fun appBuildGradle(packageName: String) = """
        |plugins {
        |    id("com.android.application")
        |    id("org.jetbrains.kotlin.plugin.compose")
        |}
        |
        |android {
        |    namespace = "$packageName"
        |    compileSdk = 36
        |
        |    defaultConfig {
        |        applicationId = "$packageName"
        |        minSdk = 29
        |        targetSdk = 36
        |        versionCode = 1
        |        versionName = "1.0.0"
        |    }
        |
        |    buildTypes {
        |        release {
        |            isMinifyEnabled = false
        |            proguardFiles(
        |                getDefaultProguardFile("proguard-android-optimize.txt"),
        |                "proguard-rules.pro",
        |            )
        |        }
        |    }
        |
        |    compileOptions {
        |        sourceCompatibility = JavaVersion.VERSION_21
        |        targetCompatibility = JavaVersion.VERSION_21
        |    }
        |
        |    buildFeatures {
        |        compose = true
        |    }
        |}
        |
        |dependencies {
        |    // Fluxa framework
        |    // TODO: replace with published version once available on Maven Central
        |    // implementation("dev.fluxa:fluxa-compose:0.1.0")
        |    // implementation("dev.fluxa:fluxa-nav:0.1.0")
        |    // implementation("dev.fluxa:fluxa-effect:0.1.0")
        |    // implementation("dev.fluxa:fluxa-data:0.1.0")
        |    // implementation("dev.fluxa:fluxa-store:0.1.0")
        |
        |    val composeBom = platform("androidx.compose:compose-bom:2026.02.01")
        |    implementation(composeBom)
        |    implementation("androidx.activity:activity-compose:1.12.0")
        |    implementation("androidx.compose.foundation:foundation")
        |    implementation("androidx.compose.material3:material3")
        |    implementation("androidx.compose.ui:ui")
        |    implementation("androidx.compose.ui:ui-tooling-preview")
        |    debugImplementation("androidx.compose.ui:ui-tooling")
        |}
    """.trimMargin() + "\n"

    fun androidManifest(appLabel: String) = """
        |<manifest xmlns:android="http://schemas.android.com/apk/res/android">
        |    <application
        |        android:allowBackup="true"
        |        android:label="$appLabel"
        |        android:supportsRtl="true"
        |        android:theme="@style/Theme.$appLabel">
        |        <activity
        |            android:name=".MainActivity"
        |            android:exported="true">
        |            <intent-filter>
        |                <action android:name="android.intent.action.MAIN" />
        |                <category android:name="android.intent.category.LAUNCHER" />
        |            </intent-filter>
        |        </activity>
        |    </application>
        |</manifest>
    """.trimMargin() + "\n"

    fun themesXml(appLabel: String) = """
        |<resources>
        |    <style name="Theme.$appLabel" parent="@android:style/Theme.Material.Light.NoActionBar" />
        |</resources>
    """.trimMargin() + "\n"

    fun mainActivity(packageName: String, appLabel: String) = """
        |package $packageName
        |
        |import android.os.Bundle
        |import androidx.activity.ComponentActivity
        |import androidx.activity.compose.setContent
        |import dev.fluxa.compose.FluxaRenderContext
        |import dev.fluxa.compose.FluxaTheme
        |import dev.fluxa.compose.RenderFluxaNode
        |import dev.fluxa.style.FluxaThemes
        |import ${packageName}.ui.homeTree
        |
        |class MainActivity : ComponentActivity() {
        |    override fun onCreate(savedInstanceState: Bundle?) {
        |        super.onCreate(savedInstanceState)
        |        setContent {
        |            FluxaTheme(theme = FluxaThemes.Aurora) {
        |                RenderFluxaNode(
        |                    node = homeTree(),
        |                    context = FluxaRenderContext(),
        |                )
        |            }
        |        }
        |    }
        |}
    """.trimMargin() + "\n"

    fun homeScreen(packageName: String, appLabel: String) = """
        |package ${packageName}.ui
        |
        |import dev.fluxa.style.FluxaThemes
        |import dev.fluxa.ui.FluxaNode
        |import dev.fluxa.ui.HeroPanel
        |import dev.fluxa.ui.FeatureCard
        |import dev.fluxa.ui.PillRow
        |import dev.fluxa.ui.screen
        |
        |private val theme = FluxaThemes.Aurora
        |
        |fun homeTree(): FluxaNode = screen(
        |    HeroPanel(
        |        title = "$appLabel",
        |        subtitle = "Built with Fluxa",
        |        theme = theme,
        |    ),
        |    PillRow(
        |        labels = listOf("Fast", "Typed", "Themeable"),
        |        theme = theme,
        |    ),
        |    FeatureCard(
        |        title = "Welcome",
        |        body = "Start building your app with Fluxa components.",
        |        theme = theme,
        |    ),
        |)
    """.trimMargin() + "\n"

    fun appTheme(packageName: String) = """
        |package ${packageName}.ui.theme
        |
        |import androidx.compose.runtime.Composable
        |import dev.fluxa.compose.FluxaTheme
        |import dev.fluxa.style.FluxaThemeTokens
        |import dev.fluxa.style.FluxaThemes
        |
        |@Composable
        |fun AppThemeWrapper(
        |    theme: FluxaThemeTokens = FluxaThemes.Aurora,
        |    content: @Composable () -> Unit,
        |) {
        |    FluxaTheme(theme = theme, content = content)
        |}
    """.trimMargin() + "\n"

    fun gradleProperties() = """
        |org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
        |android.useAndroidX=true
        |kotlin.code.style=official
        |android.nonTransitiveRClass=true
    """.trimMargin() + "\n"

    fun gitignore() = """
        |*.iml
        |.gradle
        |/local.properties
        |.idea
        |.DS_Store
        |/build
        |/captures
        |.externalNativeBuild
        |.cxx
        |*.apk
        |*.aab
    """.trimMargin() + "\n"

    // --- Generator templates ---

    fun screen(packageName: String, screenName: String) = """
        |package ${packageName}.ui
        |
        |import dev.fluxa.style.FluxaThemes
        |import dev.fluxa.ui.FluxaNode
        |import dev.fluxa.ui.SectionHeader
        |import dev.fluxa.ui.FeatureCard
        |import dev.fluxa.ui.screen
        |
        |private val theme = FluxaThemes.Aurora
        |
        |fun ${screenName.replaceFirstChar { it.lowercase() }}Tree(): FluxaNode = screen(
        |    SectionHeader(title = "$screenName", theme = theme),
        |    FeatureCard(
        |        title = "$screenName",
        |        body = "This is the $screenName screen.",
        |        theme = theme,
        |    ),
        |)
    """.trimMargin() + "\n"

    fun component(packageName: String, componentName: String) = """
        |package ${packageName}.ui.components
        |
        |import dev.fluxa.style.FluxaStyle
        |import dev.fluxa.style.FluxaThemeTokens
        |import dev.fluxa.style.FluxaThemes
        |import dev.fluxa.ui.FluxaNode
        |import dev.fluxa.ui.column
        |import dev.fluxa.ui.text
        |
        |fun $componentName(
        |    theme: FluxaThemeTokens = FluxaThemes.Aurora,
        |): FluxaNode = column(
        |    text("$componentName"),
        |)
    """.trimMargin() + "\n"

    fun featureModuleBuildGradle(packageName: String) = """
        |plugins {
        |    id("com.android.library")
        |    id("org.jetbrains.kotlin.plugin.compose")
        |}
        |
        |android {
        |    namespace = "$packageName"
        |    compileSdk = 36
        |
        |    defaultConfig {
        |        minSdk = 29
        |    }
        |
        |    compileOptions {
        |        sourceCompatibility = JavaVersion.VERSION_21
        |        targetCompatibility = JavaVersion.VERSION_21
        |    }
        |
        |    buildFeatures {
        |        compose = true
        |    }
        |}
        |
        |dependencies {
        |    val composeBom = platform("androidx.compose:compose-bom:2026.02.01")
        |    implementation(composeBom)
        |    implementation("androidx.compose.foundation:foundation")
        |    implementation("androidx.compose.material3:material3")
        |    implementation("androidx.compose.ui:ui")
        |}
    """.trimMargin() + "\n"

    fun featureScreen(packageName: String, featureName: String) = """
        |package $packageName
        |
        |import androidx.compose.foundation.layout.Arrangement
        |import androidx.compose.foundation.layout.Column
        |import androidx.compose.foundation.layout.fillMaxSize
        |import androidx.compose.foundation.layout.padding
        |import androidx.compose.material3.MaterialTheme
        |import androidx.compose.material3.Text
        |import androidx.compose.runtime.Composable
        |import androidx.compose.ui.Modifier
        |import androidx.compose.ui.unit.dp
        |
        |@Composable
        |fun ${featureName}Screen() {
        |    Column(
        |        modifier = Modifier.fillMaxSize().padding(24.dp),
        |        verticalArrangement = Arrangement.spacedBy(16.dp),
        |    ) {
        |        Text(
        |            text = "$featureName",
        |            style = MaterialTheme.typography.headlineMedium,
        |        )
        |    }
        |}
    """.trimMargin() + "\n"
}
