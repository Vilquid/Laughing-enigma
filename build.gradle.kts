plugins {
	// Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
	id("org.jetbrains.kotlin.jvm") version "1.5.0"

	// Apply the application plugin to add support for building a CLI application in Java.
	application
}

repositories {
	// Use Maven Central for resolving dependencies.
	mavenCentral()
}

dependencies {
	// GSON
	implementation("com.google.code.gson:gson:2.8.6")

	// API Fuel
	implementation("com.github.kittinunf.fuel:fuel:2.3.0")
	implementation("com.github.kittinunf.fuel:fuel-coroutines:2.3.0")
	implementation("com.github.kittinunf.fuel:fuel-gson:2.3.0")

	// log4j
	implementation("org.apache.logging.log4j:log4j-api-kotlin:1.0.0")
	implementation("org.apache.logging.log4j:log4j-api:2.11.1")
	implementation("org.apache.logging.log4j:log4j-core:2.11.1")

	// Co-routines
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

	// Align versions of all Kotlin components
	implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

	// Use the Kotlin JDK 8 standard library.
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// This dependency is used by the application.
	implementation("com.google.guava:guava:30.1.1-jre")

	// Use the Kotlin test library.
	testImplementation("org.jetbrains.kotlin:kotlin-test")

	// Use the Kotlin JUnit integration.
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
	// Define the main class for the application.
	mainClass.set("org.isen.papernews.AppKt")
}
