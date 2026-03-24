package dev.fluxa.cli

import java.io.File

object GenerateCommand {
    fun run(args: List<String>) {
        if (args.size < 2) {
            System.err.println("Usage: fluxa generate <type> <name> [--package <pkg>]")
            System.err.println("Types: screen, component, feature")
            return
        }

        val type = args[0]
        val name = args[1]
        val packageOverride = parseFlag(args, "--package")

        when (type) {
            "screen" -> generateScreen(name, packageOverride)
            "component" -> generateComponent(name, packageOverride)
            "feature" -> generateFeature(name, packageOverride)
            else -> System.err.println("Unknown generator type: $type. Use: screen, component, feature")
        }
    }

    private fun parseFlag(args: List<String>, flag: String): String? {
        val index = args.indexOf(flag)
        return if (index >= 0 && index + 1 < args.size) args[index + 1] else null
    }

    private fun generateScreen(name: String, packageOverride: String?) {
        val root = findProjectRoot()
        if (root == null) {
            System.err.println("Could not find a Gradle project root. Run this from inside a Fluxa project.")
            return
        }

        val packageName = packageOverride ?: detectPackage(root)
        if (packageName == null) {
            System.err.println("Could not detect package name. Use --package to specify it.")
            return
        }

        val screenName = name.toPascalCase()
        val packagePath = packageName.replace(".", "/")
        val file = File(root, "app/src/main/kotlin/$packagePath/ui/${screenName}Screen.kt")

        if (file.exists()) {
            System.err.println("File already exists: ${file.path}")
            return
        }

        println("Generating screen: $screenName")
        writeFile(file, Templates.screen(packageName, screenName))
        println("Done.")
    }

    private fun generateComponent(name: String, packageOverride: String?) {
        val root = findProjectRoot()
        if (root == null) {
            System.err.println("Could not find a Gradle project root. Run this from inside a Fluxa project.")
            return
        }

        val packageName = packageOverride ?: detectPackage(root)
        if (packageName == null) {
            System.err.println("Could not detect package name. Use --package to specify it.")
            return
        }

        val componentName = name.toPascalCase()
        val packagePath = packageName.replace(".", "/")
        val file = File(root, "app/src/main/kotlin/$packagePath/ui/components/$componentName.kt")

        if (file.exists()) {
            System.err.println("File already exists: ${file.path}")
            return
        }

        println("Generating component: $componentName")
        writeFile(file, Templates.component(packageName, componentName))
        println("Done.")
    }

    private fun generateFeature(name: String, packageOverride: String?) {
        val root = findProjectRoot()
        if (root == null) {
            System.err.println("Could not find a Gradle project root. Run this from inside a Fluxa project.")
            return
        }

        val basePackage = packageOverride ?: detectPackage(root)
        if (basePackage == null) {
            System.err.println("Could not detect package name. Use --package to specify it.")
            return
        }

        val featureName = name.toPascalCase()
        val moduleSlug = name.lowercase().replace(Regex("[^a-z0-9]"), "-")
        val featurePackage = "$basePackage.feature.${moduleSlug.replace("-", "")}"
        val packagePath = featurePackage.replace(".", "/")
        val moduleDir = File(root, "features/$moduleSlug")

        if (moduleDir.exists()) {
            System.err.println("Feature module already exists: ${moduleDir.path}")
            return
        }

        println("Generating feature module: $moduleSlug")

        writeFile(
            File(moduleDir, "build.gradle.kts"),
            Templates.featureModuleBuildGradle(featurePackage),
        )

        writeFile(
            File(moduleDir, "src/main/kotlin/$packagePath/${featureName}Screen.kt"),
            Templates.featureScreen(featurePackage, featureName),
        )

        // Register in settings.gradle.kts
        val settingsFile = File(root, "settings.gradle.kts")
        if (settingsFile.exists()) {
            val content = settingsFile.readText()
            if (!content.contains("features:$moduleSlug")) {
                val updated = content.trimEnd() + "\ninclude(\":features:$moduleSlug\")\n"
                settingsFile.writeText(updated)
                println("  updated settings.gradle.kts")
            }
        }

        println("Done. Add implementation(project(\":features:$moduleSlug\")) to your app dependencies.")
    }

    private fun detectPackage(root: File): String? {
        val buildFile = File(root, "app/build.gradle.kts")
        if (buildFile.exists()) {
            val match = Regex("""namespace\s*=\s*"([^"]+)"""").find(buildFile.readText())
            if (match != null) return match.groupValues[1]
        }
        return null
    }
}
