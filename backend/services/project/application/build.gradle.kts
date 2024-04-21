plugins {
  id("yatt.service-application-conventions")
}

group = "${group}.project"

dependencies {
  implementation(project(":services:company:api"))
  implementation(project(":services:project:api"))
  implementation(project(":services:user:api"))
}
