plugins {
    id("java")
    id("application")
}

group = "dev.mirrex"
version = "1.0-SNAPSHOT"

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "dev.mirrex.Application"
    }
}

application {
    mainClass.set("dev.mirrex.Application")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
