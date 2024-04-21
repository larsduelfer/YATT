plugins {
  id("yatt.kotlin-conventions")
  id("io.spring.dependency-management")
  kotlin("plugin.spring")
  kotlin("plugin.jpa")
}

group = "${group}.common"

dependencies {
  implementation(platform("org.junit:junit-bom:5.8.1"))
  implementation("org.junit.jupiter:junit-jupiter-api")
  implementation("org.apache.commons:commons-lang3:3.11")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}