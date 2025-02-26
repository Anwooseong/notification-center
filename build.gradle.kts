plugins {
	java
	id("org.springframework.boot") version "3.4.3"
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

allprojects {
	group = "org.example"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply{
		plugin("java")
		plugin("org.springframework.boot")
		plugin("io.spring.dependency-management")
		plugin("java-library")
	}

	java {
		toolchain {
			languageVersion.set(JavaLanguageVersion.of(21))
		}
	}

	dependencies {
		testImplementation(platform("org.junit:junit-bom:5.9.1"))
		testImplementation("org.junit.jupiter:junit-jupiter")
	}

	tasks.test {
		useJUnitPlatform()
	}
}

