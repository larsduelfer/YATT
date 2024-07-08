plugins {
  id("yatt.service-application-conventions")
}

group = "${group}.project"

dependencies {
  implementation(project(":services:company:company-api"))
  implementation(project(":services:project:project-api"))
  implementation(project(":services:user:user-api"))
}
