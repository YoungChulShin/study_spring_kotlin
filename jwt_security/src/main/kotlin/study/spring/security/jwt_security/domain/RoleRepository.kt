package study.spring.security.jwt_security.domain

import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {

    fun findByName(name: String): Role?
}