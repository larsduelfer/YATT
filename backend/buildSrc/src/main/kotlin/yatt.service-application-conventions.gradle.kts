import org.gradle.kotlin.dsl.kotlin

plugins {
  id("yatt.kotlin-conventions")
  id("org.springframework.boot")
  id("io.spring.dependency-management")
  kotlin("plugin.spring")
  kotlin("plugin.jpa")
}

group = "yatt"

dependencies {
  implementation(project(":services:common:api"))
  implementation(project(":services:common:application"))
  implementation(project(":services:common:auditing"))

  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("io.opentracing.contrib:opentracing-spring-jaeger-cloud-starter")
  implementation("org.axonframework:axon-spring-boot-starter")
  implementation("org.axonframework.extensions.kotlin:axon-kotlin")
  implementation("org.axonframework.extensions.tracing:axon-tracing-spring-boot-starter")
  implementation("org.flywaydb:flyway-core")
  implementation("org.flywaydb:flyway-database-postgresql")
  implementation("org.postgresql:postgresql")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")

  testImplementation(project(":services:common:schema-export"))
  testImplementation("com.h2database:h2")
  testImplementation("org.axonframework:axon-test")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
}