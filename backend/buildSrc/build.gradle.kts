plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

val kotlinVersion: String by project
val springBootVersion: String by project
val springDependencyManagementPluginVersion: String by project

dependencies {
    implementation("io.spring.gradle:dependency-management-plugin:$springDependencyManagementPluginVersion")
    implementation("org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin:$kotlinVersion")
    implementation("org.jetbrains.kotlin.plugin.jpa:org.jetbrains.kotlin.plugin.jpa.gradle.plugin:$kotlinVersion")
    implementation("org.jetbrains.kotlin.plugin.spring:org.jetbrains.kotlin.plugin.spring.gradle.plugin:$kotlinVersion")
    implementation("org.springframework.boot:org.springframework.boot.gradle.plugin:$springBootVersion")
}