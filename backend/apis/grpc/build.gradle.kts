plugins {
  id("com.google.protobuf") version "0.8.18"
  id("yatt.api-conventions")
}

group = "${group}.apis"

dependencies {
  api(project(":apis:grpc-lib"))

  implementation("io.projectreactor:reactor-core")
  implementation("jakarta.annotation:jakarta.annotation-api:2.0.0")
  implementation("net.devh:grpc-spring-boot-starter:2.13.1.RELEASE")
}
