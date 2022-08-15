package study.spring.security.jwt_security.api.model

import study.spring.security.jwt_security.service.model.CreateUserCommand

data class CreateUserRequestDto(
    val name: String,
    val username: String,
    val password: String
) {
    fun toCommand(): CreateUserCommand {
        return CreateUserCommand(
            name = name,
            username = username,
            password = password)
    }
}

data class AddRoleToUserRequestDto(
    val username: String,
    val roleName: String,
)