import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.1" apply false
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    kotlin("plugin.jpa") version "1.7.22"

    kotlin("kapt") version "1.7.22"
}

java.sourceCompatibility = JavaVersion.VERSION_17

allprojects {

    group = "io.mhan"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("kotlin")
        plugin("kotlin-spring")
        plugin("io.spring.dependency-management")
        plugin("kotlin-kapt")
    }

    dependencies {
        // JWT 인증
        implementation("com.auth0:java-jwt:4.2.1")

        // Kotlin 로깅
        implementation("io.github.microutils:kotlin-logging:3.0.4")

        // Kotlin
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        // H2DB
        runtimeOnly("com.h2database:h2")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    dependencyManagement {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
    }

    tasks.register("prepareKotlinBuildScriptModel"){}

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}




