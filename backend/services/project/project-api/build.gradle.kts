plugins {
  id("yatt.service-api-conventions")
}

group = "${group}.project"

dependencies {
  implementation(project(":services:company:company-api"))
    implementation(project(":services:user:user-api"))
}
