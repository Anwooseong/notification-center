plugins {
	java
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.4"
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

ext["springCloudVersion"] = "2023.0.0"

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

		implementation("org.springdoc:springdoc-openapi-starter-common:2.2.0")
		implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	}

	dependencyManagement {
		imports {
			mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
		}
	}

	tasks.test {
		useJUnitPlatform()
	}
}

