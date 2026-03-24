package dev.fluxa.cli

import java.io.File

fun String.toPascalCase(): String = split("-", "_", " ")
    .joinToString("") { it.replaceFirstChar { c -> c.uppercase() } }

fun String.toCamelCase(): String = toPascalCase().replaceFirstChar { it.lowercase() }

fun String.toPackageName(): String = lowercase().replace(Regex("[^a-z0-9.]"), "")

fun writeFile(file: File, content: String) {
    file.parentFile?.mkdirs()
    file.writeText(content)
    println("  created ${file.path}")
}

fun ensureDirectory(dir: File) {
    if (!dir.exists()) {
        dir.mkdirs()
    }
}

fun findProjectRoot(): File? {
    var dir = File(System.getProperty("user.dir"))
    while (dir.parentFile != null) {
        if (File(dir, "settings.gradle.kts").exists() || File(dir, "settings.gradle").exists()) {
            return dir
        }
        dir = dir.parentFile
    }
    return null
}

fun findAppPackage(): String? {
    val root = findProjectRoot() ?: return null
    val buildFile = File(root, "app/build.gradle.kts")
    if (!buildFile.exists()) return null
    val match = Regex("""namespace\s*=\s*"([^"]+)"""").find(buildFile.readText())
    return match?.groupValues?.get(1)
}
