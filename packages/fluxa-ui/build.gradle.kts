plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    api(project(":packages:fluxa-runtime"))
    api(project(":packages:fluxa-style"))
    testImplementation(kotlin("test"))
    testImplementation(project(":packages:fluxa-test"))
}

kotlin {
    jvmToolchain(21)
}
