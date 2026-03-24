package dev.fluxa.cli

import dev.fluxa.runtime.FluxaRuntime
import java.io.File

object NewCommand {
    fun run(args: List<String>) {
        if (args.isEmpty()) {
            System.err.println("Usage: fluxa new <app-name> [--package <com.example.app>]")
            return
        }

        val appName = args[0]
        val packageName = parseFlag(args, "--package")
            ?: "dev.fluxa.${appName.lowercase().replace(Regex("[^a-z0-9]"), "")}"

        val projectDir = File(appName)
        if (projectDir.exists()) {
            System.err.println("Directory '$appName' already exists.")
            return
        }

        println("Creating Fluxa project: $appName")
        println("Package: $packageName")
        println()

        scaffoldProject(projectDir, appName, packageName)

        println()
        println("Done! To get started:")
        println("  cd $appName")
        println("  ./gradlew :app:installDebug")
    }

    private fun parseFlag(args: List<String>, flag: String): String? {
        val index = args.indexOf(flag)
        return if (index >= 0 && index + 1 < args.size) args[index + 1] else null
    }

    private fun scaffoldProject(root: File, appName: String, packageName: String) {
        val packagePath = packageName.replace(".", "/")
        val appLabel = appName.toPascalCase()
        val version = FluxaRuntime.VERSION

        writeFile(
            File(root, "settings.gradle.kts"),
            Templates.settingsGradle(appName),
        )

        writeFile(
            File(root, "build.gradle.kts"),
            Templates.rootBuildGradle(),
        )

        writeFile(
            File(root, "gradle.properties"),
            Templates.gradleProperties(),
        )

        writeFile(
            File(root, ".gitignore"),
            Templates.gitignore(),
        )

        // app module
        writeFile(
            File(root, "app/build.gradle.kts"),
            Templates.appBuildGradle(packageName),
        )

        writeFile(
            File(root, "app/src/main/AndroidManifest.xml"),
            Templates.androidManifest(appLabel),
        )

        writeFile(
            File(root, "app/src/main/res/values/themes.xml"),
            Templates.themesXml(appLabel),
        )

        writeFile(
            File(root, "app/src/main/kotlin/$packagePath/MainActivity.kt"),
            Templates.mainActivity(packageName, appLabel),
        )

        writeFile(
            File(root, "app/src/main/kotlin/$packagePath/ui/HomeScreen.kt"),
            Templates.homeScreen(packageName, appLabel),
        )

        writeFile(
            File(root, "app/src/main/kotlin/$packagePath/ui/theme/AppTheme.kt"),
            Templates.appTheme(packageName),
        )

        writeFile(
            File(root, "app/proguard-rules.pro"),
            "",
        )
    }
}
