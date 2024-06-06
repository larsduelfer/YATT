plugins {
  id("yatt.kotlin-conventions")
  id("org.springframework.boot")
  id("com.google.osdetector") version "1.7.0"
  kotlin("plugin.spring")
  kotlin("jvm")
}

group = "${group}.clients"

dependencies {
  implementation(platform("org.springframework.shell:spring-shell-dependencies:3.3.0"))

  if (osdetector.arch.equals("aarch_64")) {
    implementation("io.netty:netty-all")
  }

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.springframework.boot:spring-boot-starter-rsocket")
  implementation("org.springframework.security:spring-security-rsocket")
  implementation("org.springframework.shell:spring-shell-starter")
}
