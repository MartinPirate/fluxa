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
        |    // TODO: replace with published Fluxa dependency once available
        |    // implementation("dev.fluxa:fluxa-compose:<version>")
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
        |import ${packageName}.ui.HomeScreen
        |import ${packageName}.ui.theme.AppThemeWrapper
        |
        |class MainActivity : ComponentActivity() {
        |    override fun onCreate(savedInstanceState: Bundle?) {
        |        super.onCreate(savedInstanceState)
        |        setContent {
        |            AppThemeWrapper {
        |                HomeScreen()
        |            }
        |        }
        |    }
        |}
    """.trimMargin() + "\n"

    fun homeScreen(packageName: String, appLabel: String) = """
        |package ${packageName}.ui
        |
        |import androidx.compose.foundation.layout.Arrangement
        |import androidx.compose.foundation.layout.Column
        |import androidx.compose.foundation.layout.fillMaxSize
        |import androidx.compose.foundation.layout.padding
        |import androidx.compose.material3.MaterialTheme
        |import androidx.compose.material3.Surface
        |import androidx.compose.material3.Text
        |import androidx.compose.runtime.Composable
        |import androidx.compose.ui.Alignment
        |import androidx.compose.ui.Modifier
        |import androidx.compose.ui.unit.dp
        |
        |@Composable
        |fun HomeScreen() {
        |    Surface(modifier = Modifier.fillMaxSize()) {
        |        Column(
        |            modifier = Modifier.fillMaxSize().padding(24.dp),
        |            verticalArrangement = Arrangement.Center,
        |            horizontalAlignment = Alignment.CenterHorizontally,
        |        ) {
        |            Text(
        |                text = "$appLabel",
        |                style = MaterialTheme.typography.headlineLarge,
        |            )
        |            Text(
        |                text = "Built with Fluxa",
        |                style = MaterialTheme.typography.bodyLarge,
        |            )
        |        }
        |    }
        |}
    """.trimMargin() + "\n"

    fun appTheme(packageName: String) = """
        |package ${packageName}.ui.theme
        |
        |import androidx.compose.material3.MaterialTheme
        |import androidx.compose.runtime.Composable
        |
        |@Composable
        |fun AppThemeWrapper(content: @Composable () -> Unit) {
        |    MaterialTheme(content = content)
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
        |import androidx.compose.foundation.layout.Arrangement
        |import androidx.compose.foundation.layout.Column
        |import androidx.compose.foundation.layout.fillMaxSize
        |import androidx.compose.foundation.layout.padding
        |import androidx.compose.material3.MaterialTheme
        |import androidx.compose.material3.Surface
        |import androidx.compose.material3.Text
        |import androidx.compose.runtime.Composable
        |import androidx.compose.ui.Modifier
        |import androidx.compose.ui.unit.dp
        |
        |@Composable
        |fun ${screenName}Screen() {
        |    Surface(modifier = Modifier.fillMaxSize()) {
        |        Column(
        |            modifier = Modifier.fillMaxSize().padding(24.dp),
        |            verticalArrangement = Arrangement.spacedBy(16.dp),
        |        ) {
        |            Text(
        |                text = "$screenName",
        |                style = MaterialTheme.typography.headlineMedium,
        |            )
        |        }
        |    }
        |}
    """.trimMargin() + "\n"

    fun component(packageName: String, componentName: String) = """
        |package ${packageName}.ui.components
        |
        |import androidx.compose.foundation.layout.Column
        |import androidx.compose.foundation.layout.padding
        |import androidx.compose.material3.MaterialTheme
        |import androidx.compose.material3.Text
        |import androidx.compose.runtime.Composable
        |import androidx.compose.ui.Modifier
        |import androidx.compose.ui.unit.dp
        |
        |@Composable
        |fun $componentName(
        |    modifier: Modifier = Modifier,
        |) {
        |    Column(modifier = modifier.padding(16.dp)) {
        |        Text(
        |            text = "$componentName",
        |            style = MaterialTheme.typography.bodyLarge,
        |        )
        |    }
        |}
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
