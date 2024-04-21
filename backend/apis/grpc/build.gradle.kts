plugins {
  id("com.google.protobuf") version "0.8.18"
   id("yatt.kotlin-conventions")
  id("io.spring.dependency-management")
  kotlin("plugin.spring")
}

group = "${group}.apis"

dependencies {
  api(project(":apis:common"))
  api(project(":apis:grpc-lib"))

  implementation("io.projectreactor:reactor-core")
  implementation("jakarta.annotation:jakarta.annotation-api:2.0.0")

  implementation("net.devh:grpc-spring-boot-starter:2.13.1.RELEASE")
  implementation("org.axonframework:axon-spring-boot-starter")
  implementation("org.axonframework.extensions.kotlin:axon-kotlin")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
  implementation("org.springframework.boot:spring-boot-starter-security")
}
