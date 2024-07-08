plugins {
  id("yatt.api-conventions")
}

group = "${group}.apis"

dependencies {
  implementation("io.projectreactor:reactor-core")
  implementation("org.springframework.boot:spring-boot-starter-web")
}
