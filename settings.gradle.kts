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
    ":apps:demo",
    ":apps:showcase",
    ":packages:fluxa-cache",
    ":packages:fluxa-cli",
    ":packages:fluxa-compose",
    ":packages:fluxa-data",
    ":packages:fluxa-effect",
    ":packages:fluxa-nav",
    ":packages:fluxa-runtime",
    ":packages:fluxa-state",
    ":packages:fluxa-store",
    ":packages:fluxa-style",
    ":packages:fluxa-test",
    ":packages:fluxa-ui",
    ":packages:fluxa-work",
)
