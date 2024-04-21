import org.gradle.kotlin.dsl.kotlin

plugins {
  id("yatt.kotlin-conventions")
  kotlin("plugin.jpa")
}

group = "yatt"

dependencies {
  implementation(project(":services:common:api"))

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("jakarta.persistence:jakarta.persistence-api")
  implementation("org.axonframework:axon-modelling")
}