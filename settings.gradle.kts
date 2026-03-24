rootProject.name = "fluxa"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
    }
}

include(
    ":apps:showcase",
    ":packages:fluxa-cli",
    ":packages:fluxa-compose",
    ":packages:fluxa-runtime",
    ":packages:fluxa-style",
    ":packages:fluxa-ui",
)
