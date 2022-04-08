pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ComposeWaifu"
include(":app")
include(":data")
include(":domain")
include(":common-ui")
include(":ui-picture")
include(":ui-gallery")
include(":ui-collection")
include(":base")
include(":presentation")
