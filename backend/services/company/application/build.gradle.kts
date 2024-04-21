plugins {
  id("yatt.service-application-conventions")
}

group = "${group}.company"

dependencies {
  implementation(project(":services:user:api"))
  implementation(project(":services:company:api"))

  // is this needed?
  implementation("io.projectreactor:reactor-core")
}
