package study.spring.kotlin.gradlesingleproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GradleSingleprojectApplication

fun main(args: Array<String>) {
    runApplication<GradleSingleprojectApplication>(*args)
}
