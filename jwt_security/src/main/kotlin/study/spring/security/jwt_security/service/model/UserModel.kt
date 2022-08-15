package study.spring.security.jwt_security.service.model

import study.spring.security.jwt_security.domain.User

data class CreateUserCommand (
    val name: String,
    val username: String,
    val password: String
) {
    fun toEntity(): User {
        return User(
            name = name,
            username = username,
            password = password)
    }
}

data class UserInfo (
    val id: Long,
    val name: String,
    val username: String,
    val password: String,
    val roles: MutableList<RoleInfo> = mutableListOf(),
) {

    companion object {
        fun from(user: User): UserInfo {
            return UserInfo(
                user.id!!,
                user.name,
                user.username,
                user.password,
                user.roles.map { RoleInfo(it.name) }.toMutableList()
            )
        }
    }
}