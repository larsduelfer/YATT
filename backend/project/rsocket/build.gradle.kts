plugins {
  id("com.novatecgmbh.commons-kotlin")
  id("io.spring.dependency-management")
  kotlin("plugin.spring")
}

group = "${group}.project"

dependencies {
  api(project(":api"))

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("io.projectreactor:reactor-core")
  implementation("org.axonframework:axon-spring-boot-starter")
  implementation("org.axonframework.extensions.kotlin:axon-kotlin")
  implementation("org.axonframework.extensions.reactor:axon-reactor-spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter-rsocket")
  implementation("org.springframework.boot:spring-boot-starter-security")

  testImplementation("org.axonframework:axon-test")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
}