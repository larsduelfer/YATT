pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    includeBuild("../../build-logic")

    val springBootVersion: String by settings
    val kotlinVersion: String by settings
    val springDependencyManagementPluginVersion: String by settings
    plugins {
        id("io.spring.dependency-management") version springDependencyManagementPluginVersion
        id("org.springframework.boot") version springBootVersion
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.jpa") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

includeBuild("../../platforms")
includeBuild("../common")
includeBuild("../project")
includeBuild("../user")

rootProject.name = "company"
include("api")
include("application")

