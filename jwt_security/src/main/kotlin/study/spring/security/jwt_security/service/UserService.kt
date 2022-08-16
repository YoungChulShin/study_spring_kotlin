package study.spring.security.jwt_security.service

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import study.spring.security.jwt_security.domain.Role
import study.spring.security.jwt_security.domain.RoleRepository
import study.spring.security.jwt_security.domain.UserRepository
import study.spring.security.jwt_security.service.model.CreateUserCommand
import study.spring.security.jwt_security.service.model.RoleInfo
import study.spring.security.jwt_security.service.model.UserInfo

@Service
class UserService (
    val passwordEncoder: PasswordEncoder,
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findByUsername(username!!)
            ?: throw UsernameNotFoundException("User not found")
        val authorities = user.roles.map { SimpleGrantedAuthority(it.name) }.toMutableList()

        return User(user.username, passwordEncoder.encode(user.password), authorities)
    }

    @Transactional
    fun saveUser(command: CreateUserCommand): UserInfo {
        val user = userRepository.save(command.toEntity())
        return UserInfo.from(user)
    }

    @Transactional
    fun saveRole(roleName: String): RoleInfo {
        val role = roleRepository.save(Role(name = roleName))
        return RoleInfo.from(role)
    }

    @Transactional
    fun addRoleToUser(username: String, roleName: String) {
        val user = userRepository.findByUsername(username) ?: return
        val role = roleRepository.findByName(roleName) ?: return
        user.roles.add(role)
    }

    @Transactional(readOnly = true)
    fun getUser(username: String): UserInfo? {
        return userRepository.findByUsername(username)?.let { user -> UserInfo.from(user) }
    }

    @Transactional(readOnly = true)
    fun getUsers(): List<UserInfo> {
        return userRepository.findAll().map { user -> UserInfo.from(user) }.toList()
    }
}