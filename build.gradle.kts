plugins {
    id("java")
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.weather.forecast.backend"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.mysql:mysql-connector-j:8.0.33")
    implementation("org.apache.logging.log4j:log4j-core:2.24.3")
    implementation("org.hibernate:hibernate-core:6.6.0.Final")
    implementation("org.springframework.boot:spring-boot-starter-websocket:3.4.4")

    testImplementation("org.mockito:mockito-junit-jupiter:5.17.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.4.4")
    testImplementation("com.h2database:h2")

}

tasks.test {
    useJUnitPlatform()
}
