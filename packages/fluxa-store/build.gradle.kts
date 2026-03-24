plugins {
    id("com.android.library")
}

android {
    namespace = "dev.fluxa.store"
    compileSdk = 36

    defaultConfig {
        minSdk = 29
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    api(project(":packages:fluxa-runtime"))
    implementation("androidx.datastore:datastore-preferences:1.1.7")
}
