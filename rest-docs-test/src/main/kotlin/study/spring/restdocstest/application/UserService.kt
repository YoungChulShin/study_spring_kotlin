package study.spring.restdocstest.application

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import study.spring.restdocstest.domain.User
import study.spring.restdocstest.domain.UserRepository

@Service
class UserService(
    val userRepository: UserRepository,
) {

    @Transactional
    fun save(user: User) = userRepository.save(user)

    @Transactional
    fun findAll() = userRepository.findAll()

    @Transactional
    fun findUser(id: Long): User {
        return userRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException()
    }
}