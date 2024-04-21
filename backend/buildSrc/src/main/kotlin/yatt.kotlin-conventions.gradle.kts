plugins {
    id("com.diffplug.spotless")
    id("java-library")
    kotlin("jvm")
}

group = "yatt"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

val springBootVersion: String by project
val springCloudVersion: String by project
val axonVersion: String by project

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
    implementation(
        platform("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    )
    implementation(platform("org.axonframework:axon-bom:$axonVersion"))
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))

    constraints {
        implementation("io.opentracing.contrib:opentracing-spring-jaeger-cloud-starter:3.3.1")
        implementation("io.opentracing.contrib:opentracing-spring-jaeger-web-starter:3.3.1")
        implementation("io.opentracing.contrib:opentracing-spring-jaeger-starter:3.2.2")
        implementation("io.projectreactor:reactor-core:3.4.16")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extension:1.1.6")
        implementation("jakarta.persistence:jakarta.persistence-api")
    }

    testImplementation(platform("org.junit:junit-bom:5.8.1"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("com.tngtech.archunit:archunit-junit5:0.21.0")
    testImplementation("org.apache.commons:commons-lang3:3.11")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.withType<Test> { useJUnitPlatform() }

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Copy> { duplicatesStrategy = DuplicatesStrategy.WARN }

tasks.withType<Jar> { duplicatesStrategy = DuplicatesStrategy.WARN }

spotless { kotlin { ktfmt("0.47").kotlinlangStyle() } }
