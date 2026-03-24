plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    api(project(":packages:fluxa-ui"))
    api(project(":packages:fluxa-style"))
    api(project(":packages:fluxa-data"))
    implementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}
