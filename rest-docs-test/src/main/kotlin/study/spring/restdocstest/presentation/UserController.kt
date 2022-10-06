package study.spring.restdocstest.presentation

import org.springframework.web.bind.annotation.*
import study.spring.restdocstest.application.UserService
import study.spring.restdocstest.domain.User

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    val userService: UserService,
) {

    @GetMapping
    fun findUsers(): List<User> {
        return userService.findAll()
    }

    @GetMapping("/{id}")
    fun findUser(@PathVariable(value = "id") id: Long): User {
        return userService.findUser(id)
    }

    @PostMapping
    fun saveUser(@RequestBody user: User) : User {
        return userService.save(user)
    }
}