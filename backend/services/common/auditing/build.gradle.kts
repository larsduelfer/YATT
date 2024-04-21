plugins {
    id("yatt.kotlin-conventions")
}

group = "${group}.common"

dependencies {
    implementation(project(":services:common:api"))

    implementation("org.axonframework:axon-modelling")
    implementation("org.springframework.boot:spring-boot-starter-security")
}