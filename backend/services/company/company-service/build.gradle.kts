plugins {
  id("yatt.service-application-conventions")
}

group = "${group}.company"

dependencies {
  implementation(project(":services:user:user-api"))
    implementation(project(":services:company:company-api"))

    // is this needed?
  implementation("io.projectreactor:reactor-core")
}
