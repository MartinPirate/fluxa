plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    api(project(":packages:fluxa-runtime"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}
