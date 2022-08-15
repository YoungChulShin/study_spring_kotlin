package study.spring.security.jwt_security.service.model

import study.spring.security.jwt_security.domain.Role

data class RoleInfo(
    val name: String,
) {
    companion object {
        fun from(role: Role) = RoleInfo(name = role.name)
    }
}