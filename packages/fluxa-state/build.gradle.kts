plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    api(project(":packages:fluxa-runtime"))
}

kotlin {
    jvmToolchain(21)
}
