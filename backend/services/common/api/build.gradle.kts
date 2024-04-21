plugins {
    id("yatt.kotlin-conventions")
    kotlin("plugin.jpa")
}

group = "${group}.common"

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.axonframework:axon-modelling")
    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("org.springframework.boot:spring-boot-starter-security")
}
