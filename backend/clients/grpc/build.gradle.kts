plugins {
  id("yatt.kotlin-conventions")
  id("org.springframework.boot")
  kotlin("plugin.spring")
  kotlin("jvm")
}

group = "${group}.clients"

dependencies {
  implementation(platform("org.springframework.shell:spring-shell-dependencies:3.3.0"))

  implementation(project(":apis:grpc-lib"))

  implementation("com.google.protobuf:protobuf-java-util:3.19.2")
  implementation("net.devh:grpc-client-spring-boot-starter:2.13.1.RELEASE")
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework.shell:spring-shell-starter")
  implementation("org.springframework:spring-web")
}
