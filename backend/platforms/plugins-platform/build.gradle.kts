plugins {
    id("java-platform")
}

group = "com.novatecgmbh.platform"

dependencies {
    constraints {
        api("io.spring.gradle:dependency-management-plugin:1.0.11.RELEASE")
        api("org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin:1.5.21")
        api("org.jetbrains.kotlin.plugin.jpa:org.jetbrains.kotlin.plugin.jpa.gradle.plugin:1.5.21")
        api("org.jetbrains.kotlin.plugin.spring:org.jetbrains.kotlin.plugin.spring.gradle.plugin:1.5.21")
        api("org.springframework.boot:org.springframework.boot.gradle.plugin:2.5.3")
    }
}