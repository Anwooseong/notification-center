dependencies {
    //core
    implementation(project(mapOf("path" to ":core")))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-stream-kafka")
}