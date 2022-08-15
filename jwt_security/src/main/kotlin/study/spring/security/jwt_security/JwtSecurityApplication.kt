package study.spring.security.jwt_security

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JwtSecurityApplication

fun main(args: Array<String>) {
    runApplication<JwtSecurityApplication>(*args)
}
