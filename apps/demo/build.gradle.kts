plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "dev.fluxa.demo"
    compileSdk = 36

    defaultConfig {
        applicationId = "dev.fluxa.demo"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "0.1.0-dev"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":packages:fluxa-compose"))
    implementation(project(":packages:fluxa-effect"))
    implementation(project(":packages:fluxa-nav"))
    implementation(project(":packages:fluxa-data"))
    implementation(project(":packages:fluxa-cache"))
    implementation(project(":packages:fluxa-store"))
    implementation(project(":packages:fluxa-work"))

    val composeBom = platform("androidx.compose:compose-bom:2026.02.01")
    implementation(composeBom)
    implementation("androidx.activity:activity-compose:1.12.0")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    testImplementation("junit:junit:4.13.2")
    testImplementation(project(":packages:fluxa-test"))
}
