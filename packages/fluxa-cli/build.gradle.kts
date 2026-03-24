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
    applicationName = "fluxa"
}

kotlin {
    jvmToolchain(21)
}

tasks.named<CreateStartScripts>("startScripts") {
    applicationName = "fluxa"
}
