package study.spring.entitylocktest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EntityLockTestApplication

fun main(args: Array<String>) {
    runApplication<EntityLockTestApplication>(*args)
}
