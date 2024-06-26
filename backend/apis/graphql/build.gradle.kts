plugins {
  id("yatt.kotlin-conventions")
  id("io.spring.dependency-management")
  kotlin("plugin.spring")
}

group = "${group}.apis"

dependencies {
  api(project(":apis:common"))

  implementation("io.opentracing.contrib:opentracing-spring-jaeger-cloud-starter")
  implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.5")
  implementation("org.axonframework:axon-spring-boot-starter")
  implementation("org.axonframework.extensions.kotlin:axon-kotlin")
  implementation("org.axonframework.extensions.tracing:axon-tracing-spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter-graphql")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-websocket")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
}