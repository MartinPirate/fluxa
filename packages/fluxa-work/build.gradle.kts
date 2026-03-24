plugins {
    id("com.android.library")
}

android {
    namespace = "dev.fluxa.work"
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
    api("androidx.work:work-runtime-ktx:2.10.1")
}
