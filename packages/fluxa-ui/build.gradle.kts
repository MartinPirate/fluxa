plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    api(project(":packages:fluxa-runtime"))
    api(project(":packages:fluxa-style"))
}

kotlin {
    jvmToolchain(21)
}
