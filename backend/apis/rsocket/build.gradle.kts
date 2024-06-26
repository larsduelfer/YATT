plugins {
  id("yatt.kotlin-conventions")
  id("io.spring.dependency-management")
  id("com.google.osdetector") version "1.7.0"
  kotlin("plugin.spring")
}

group = "${group}.apis"

dependencies {
  api(project(":apis:common"))

  if (osdetector.arch.equals("aarch_64")) {
    implementation("io.netty:netty-all")
  }

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("io.projectreactor:reactor-core")
  implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
  implementation("org.axonframework:axon-spring-boot-starter")
  implementation("org.axonframework.extensions.kotlin:axon-kotlin")
  implementation("org.axonframework.extensions.reactor:axon-reactor-spring-boot-starter")
  implementation("org.axonframework.extensions.tracing:axon-tracing-spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
  implementation("org.springframework.boot:spring-boot-starter-rsocket")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.security:spring-security-messaging")
  implementation("org.springframework.security:spring-security-rsocket")

  testImplementation("org.axonframework:axon-test")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
}
