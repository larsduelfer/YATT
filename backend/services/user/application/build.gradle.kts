plugins {
  id("yatt.service-application-conventions")
}

group = "${group}.user"

dependencies {
  implementation(project(":services:user:api"))
}
