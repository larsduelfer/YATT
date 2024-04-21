plugins {
  id("yatt.kotlin-conventions")
  id("io.spring.dependency-management")
  kotlin("plugin.spring")
  kotlin("plugin.jpa")
}

group = "${group}.common"

val junitVersion: String by project

dependencies {
  implementation(platform("org.junit:junit-bom:$junitVersion"))
  implementation("org.junit.jupiter:junit-jupiter-api")
  implementation("org.apache.commons:commons-lang3:3.11")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.hibernate.tool:hibernate-tools-ant:6.4.4.Final")
  implementation("org.hibernate.orm:hibernate-community-dialects:6.4.4.Final")
}
