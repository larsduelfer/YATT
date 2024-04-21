plugins {
  id("yatt.service-api-conventions")
}

group = "${group}.project"

dependencies {
  implementation(project(":services:company:api"))
  implementation(project(":services:user:api"))
}
