plugins {
    id("yatt.service-api-conventions")
}

group = "${group}.company"
dependencies {
    implementation(project(":services:user:user-api"))
}

