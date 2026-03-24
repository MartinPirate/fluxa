plugins {
    id("com.android.application") version "9.1.0" apply false
    id("com.android.library") version "9.1.0" apply false
    base
    kotlin("jvm") version "2.3.10" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.3.10" apply false
}

subprojects {
    group = "dev.fluxa"
    version = "0.1.0-dev"
}
