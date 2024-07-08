plugins {
  id("yatt.api-conventions")
  id("com.google.osdetector") version "1.7.0"
}

group = "${group}.apis"

dependencies {

  if (osdetector.arch.equals("aarch_64")) {
    implementation("io.netty:netty-all")
  }

  implementation("io.projectreactor:reactor-core")
  implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
  implementation("org.axonframework.extensions.reactor:axon-reactor-spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter-rsocket")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.security:spring-security-messaging")
  implementation("org.springframework.security:spring-security-rsocket")
}
