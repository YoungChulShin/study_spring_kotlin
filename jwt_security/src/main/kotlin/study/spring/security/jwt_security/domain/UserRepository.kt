package study.spring.security.jwt_security.domain

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun findByUsername(username: String): User?
}