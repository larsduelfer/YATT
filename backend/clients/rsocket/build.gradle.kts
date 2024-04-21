plugins {
  id("yatt.kotlin-conventions")
  id("org.springframework.boot")
  kotlin("plugin.spring")
  kotlin("jvm")
}

group = "${group}.clients"

dependencies {
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.springframework.boot:spring-boot-starter-rsocket")
  implementation("org.springframework.security:spring-security-rsocket")
  implementation("org.springframework.shell:spring-shell-starter:2.1.0-M3")
}
