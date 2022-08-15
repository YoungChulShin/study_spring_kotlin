package study.spring.security.jwt_security.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import study.spring.security.jwt_security.api.model.AddRoleToUserRequestDto
import study.spring.security.jwt_security.api.model.CreateRoleRequestDto
import study.spring.security.jwt_security.api.model.CreateUserRequestDto
import study.spring.security.jwt_security.service.UserService
import study.spring.security.jwt_security.service.model.RoleInfo
import study.spring.security.jwt_security.service.model.UserInfo

@RestController
@RequestMapping("/api")
class UserController(
    val userService: UserService
) {
    @GetMapping("/users")
    fun getUsers(): List<UserInfo> {
        return userService.getUsers()
    }

    @PostMapping("/users")
    fun saveUsers(request: CreateUserRequestDto): UserInfo {
        return userService.saveUser(request.toCommand())
    }

    @PostMapping("/roles")
    fun saveRole(request: CreateRoleRequestDto): RoleInfo {
        return userService.saveRole(request.name)
    }

    @PostMapping("/users/add-role")
    fun addRoleToUser(request: AddRoleToUserRequestDto) {
        userService.addRoleToUser(request.username, request.roleName)
    }
}