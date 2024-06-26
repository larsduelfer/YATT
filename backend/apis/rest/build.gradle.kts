plugins {
  id("yatt.kotlin-conventions")
  id("io.spring.dependency-management")
  kotlin("plugin.spring")
}

group = "${group}.apis"

dependencies {
  api(project(":apis:common"))

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("io.opentracing.contrib:opentracing-spring-jaeger-web-starter")
  implementation("io.projectreactor:reactor-core")
  implementation("org.axonframework:axon-spring-boot-starter")
  implementation("org.axonframework.extensions.kotlin:axon-kotlin")
  implementation("org.axonframework.extensions.tracing:axon-tracing-spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

  testImplementation("org.axonframework:axon-test")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
}
