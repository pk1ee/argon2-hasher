plugins {
    id("java")
}

group = "dev.nautchkafe.studios.network.sdk.argon"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // argon stuff
    implementation("de.mkammerer:argon2-jvm:2.12")
}
