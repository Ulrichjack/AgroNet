pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")   // Corrigé : com au lieu de cm
                includeGroupByRegex("com\\.google.*")    // Corrigé : com au lieu de cm
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AgroNet"
include(":app")