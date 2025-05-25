import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
  `java-library`
}

group = "co.selim"
version = "1.0.0-SNAPSHOT"

repositories {
  mavenCentral()
}

val vertxVersion = "5.0.0"
val junitJupiterVersion = "5.9.1"

dependencies {
  testImplementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
  testImplementation("io.vertx:vertx-pg-client")
  testRuntimeOnly("com.ongres.scram:client:2.1")
  testImplementation("org.testcontainers:testcontainers:1.21.0")
  testImplementation("org.testcontainers:postgresql:1.21.0")
  testImplementation("org.testcontainers:junit-jupiter:1.21.0")
  testImplementation("io.vertx:vertx-junit5")
  testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events = setOf(PASSED, SKIPPED, FAILED)
  }
}
