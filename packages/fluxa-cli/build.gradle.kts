plugins {
    application
    kotlin("jvm")
}

dependencies {
    implementation(project(":packages:fluxa-ui"))
    implementation(project(":packages:fluxa-style"))
}

application {
    mainClass.set("dev.fluxa.cli.MainKt")
}

kotlin {
    jvmToolchain(21)
}
