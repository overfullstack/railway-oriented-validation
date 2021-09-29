plugins {
  java
  id("io.freefair.lombok") version "6.2.0"
  id("org.sonarqube") version "3.3"
}

group = "com.gakshintala.fp"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

tasks {
  withType<JavaCompile> {
    options.compilerArgs.addAll(arrayOf("--enable-preview"))
    options.encoding = "UTF-8"
  }
  test {
    useJUnitPlatform()
  }
}

dependencies {
  implementation("io.vavr:vavr:+")
  implementation("org.slf4j:slf4j-api:+")
  runtimeOnly("org.apache.logging.log4j:log4j-slf4j18-impl:+")

  testImplementation(platform("org.junit:junit-bom:+"))
  testImplementation("org.junit.jupiter:junit-jupiter-api")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}
