plugins {
	java
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.7"
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

allprojects {
	group = "com.ws"
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
		compileOnly ("org.projectlombok:lombok")
		annotationProcessor ("org.projectlombok:lombok")

		testImplementation(platform("org.junit:junit-bom:5.10.0"))
		testImplementation("org.junit.jupiter:junit-jupiter")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
	}

	tasks.test {
		useJUnitPlatform()
	}
}

