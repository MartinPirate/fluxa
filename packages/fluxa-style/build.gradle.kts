plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    api(project(":packages:fluxa-runtime"))
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}
