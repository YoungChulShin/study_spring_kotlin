import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    // id("org.asciidoctor.convert") version "1.5.8"
    // 기본 설정은 예전 gradle 버전으로 되어 있어서 플러그인 변경
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "study.spring"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

val snippetDir = file("build/generated-snippets")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    runtimeOnly("mysql:mysql-connector-java")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // RestDocs 기반 테스트 진행
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    println("test 실행")
//    outputs.dir(snippetDir)
}

tasks.asciidoctor {
    println("asciidoctor 실행")
    inputs.dir(snippetDir)
    dependsOn(tasks.test)

    doFirst {
        println("파일 삭제 실행")
        delete {
            file("src/main/resources/static/docs")
        }
    }
}

tasks.register("copyHTML", Copy::class) {
    println("copyHTML 실행")
    dependsOn(tasks.asciidoctor)
    from(file("build/docs/asciidoc"))
    into(file("src/main/resources/static/docs"))
}

tasks.bootJar {
    println("bootJar 실행")
    dependsOn(tasks.asciidoctor)
    dependsOn(tasks.getByName("copyHTML"))
}