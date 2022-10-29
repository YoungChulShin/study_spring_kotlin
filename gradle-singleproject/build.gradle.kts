import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// kotlin: Kotlin gradle plugin
plugins {
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    // JVM 대상일 경우에 아래 플러그인을 추가한다
    // 또는 'apply plugin: 'kotlin'' 방법을 쓸 수 있지만 추천하지는 않는다
    // -> https://kotlinlang.org/docs/gradle.html#using-the-gradle-kotlin-dsl
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "study.spring.kotlin"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // 리플렉션을 사용하기 위한 라이브러리
    // https://kotlinlang.org/docs/reflection.html
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // kotlin standard library
    // kotlin-stdlib는 java 6와 그 이상의 버전을 지원하기 위한 것
    // kotlin-stdlib-jdk7, jdk8은 이를 확장해서 jdk7, 8의 새로운 기능을 추가한 것
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// 컴파일 옵션: https://kotlinlang.org/docs/gradle.html#attributes-specific-to-js
tasks.withType<KotlinCompile> {
    kotlinOptions {
        // JSR305: https://jcp.org/en/jsr/detail?id=305
        // Kotin JSR305 support: https://kotlinlang.org/docs/java-interop.html#jsr-305-support
        // https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/boot-features-kotlin.html
        // null check를 어떻게 할 것인지에 대한 정의
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    // Specifies that JUnit Platform should be used to discover and execute the tests with additional configuration.
    // Use this option if your tests use JUnit Jupiter/JUnit5.
    // JUnit5 테스트 시 사용
    useJUnitPlatform()
}
