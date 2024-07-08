plugins {
  id("yatt.api-conventions")
}

group = "${group}.apis"

dependencies {
  implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.5")

  implementation("org.springframework.boot:spring-boot-starter-graphql")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-websocket")
}