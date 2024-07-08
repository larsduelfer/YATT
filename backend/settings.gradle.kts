pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        maven("https://repo.spring.io/milestone")
        maven("https://repo.spring.io/snapshot")
    }

    val springBootVersion: String by settings
    val kotlinVersion: String by settings
    val springDependencyManagementPluginVersion: String by settings
    val spotlessVersion: String by settings

    plugins {
        id("io.spring.dependency-management") version springDependencyManagementPluginVersion
        id("org.springframework.boot") version springBootVersion
        id("com.diffplug.spotless") version spotlessVersion
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.jpa") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven ("https://repo.spring.io/milestone")
        maven("https://repo.spring.io/snapshot")
    }
}

rootProject.name = "backend"

include(":services:common:api")
include(":services:common:application")
include(":services:common:auditing")
include(":services:common:schema-export")
include(":services:user:user-api")
include(":services:user:user-service")
include(":services:company:company-api")
include(":services:company:company-service")
include(":services:project:project-api")
include(":services:project:project-service")

include(":apis:common")
include(":apis:graphql")
include(":apis:grpc")
include(":apis:grpc-lib")
include(":apis:rest")
include(":apis:rsocket")

include(":data-import:initial")

include(":clients:grpc")
include(":clients:rsocket")
