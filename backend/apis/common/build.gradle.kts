plugins {
  id("yatt.kotlin-conventions")
  id("io.spring.dependency-management")
  kotlin("plugin.spring")
}

group = "${group}.apis"

dependencies {
  api(project(":services:common:api"))
  api(project(":services:common:auditing"))
  api(project(":services:company:api"))
  api(project(":services:project:api"))
  api(project(":services:user:api"))

  implementation("org.axonframework:axon-spring-boot-starter")
  implementation("org.axonframework.extensions.kotlin:axon-kotlin")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
  implementation("org.springframework.boot:spring-boot-starter-security")
}
