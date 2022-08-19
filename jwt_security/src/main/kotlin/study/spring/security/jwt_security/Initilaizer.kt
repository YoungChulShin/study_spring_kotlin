package study.spring.security.jwt_security

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import study.spring.security.jwt_security.service.UserService
import study.spring.security.jwt_security.service.model.CreateUserCommand

@Component
class Initilaizer(
    val userService: UserService
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        userService.saveRole("ROLE_USER")
        userService.saveRole("ROLE_MANAGER")
        userService.saveRole("ROLE_ADMIN")
        userService.saveRole("ROLE_SUPER_ADMIN")

        userService.saveUser(CreateUserCommand("shin", "shin", "1323"))
        userService.saveUser(CreateUserCommand("kim", "kim", "1323"))
        userService.saveUser(CreateUserCommand("kang", "kang", "1323"))
        userService.saveUser(CreateUserCommand("min", "min", "1323"))

        userService.addRoleToUser("shin", "ROLE_USER")
        userService.addRoleToUser("shin", "ROLE_MANAGER")
        userService.addRoleToUser("kim", "ROLE_MANAGER")
        userService.addRoleToUser("kang", "ROLE_ADMIN")
        userService.addRoleToUser("min", "ROLE_SUPER_ADMIN")
        userService.addRoleToUser("min", "ROLE_ADMIN")
        userService.addRoleToUser("min", "ROLE_USER")
    }
}