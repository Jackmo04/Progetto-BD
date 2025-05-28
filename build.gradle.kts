plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application
    application

    // Task to create a fat JAR
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.mysql:mysql-connector-j:9.3.0")
    testImplementation("org.assertj:assertj-core:3.27.3")

    // SLF4J for logging abstraction
    implementation("org.slf4j:slf4j-api:2.0.16")
    // Logback backend for SLF4J
    runtimeOnly("ch.qos.logback:logback-classic:1.5.12")

    // JUnit API and testing engine
    val jUnitVersion = "5.11.3"
    // when dependencies share the same version, grouping in a val helps to keep them in sync
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

application {
    mainClass = "porto.App"
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events(*org.gradle.api.tasks.testing.logging.TestLogEvent.values())
        showStandardStreams = true
    }
}
