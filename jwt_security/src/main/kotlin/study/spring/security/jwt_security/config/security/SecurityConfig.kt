package study.spring.security.jwt_security.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import study.spring.security.jwt_security.config.security.filter.CustomAuthenticationFilter

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화, preAuthorize 어노테이션 활성화
class SecurityConfig(
    private val tokenProvider: TokenProvider,
) {
    @Bean
    fun filterChain(http: HttpSecurity, authenticationManager: AuthenticationManager): SecurityFilterChain {
        val customAuthenticationFilter = CustomAuthenticationFilter(
            authenticationManager,
            tokenProvider)
        customAuthenticationFilter.setFilterProcessesUrl("/api/login")

        http.csrf().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authorizeHttpRequests()
            .antMatchers("/login/**")
            .permitAll()
            .antMatchers(HttpMethod.GET, "/api/user/**")
            .hasAnyAuthority("ROLE_USER")
            .antMatchers(HttpMethod.POST, "/api/user/save/**")
            .hasAnyAuthority("ROLE_ADMIN")
            .anyRequest()
            .authenticated()
        http.addFilter(customAuthenticationFilter)

        return http.build()
    }

    @Bean
    fun authenticationManager(
        http: HttpSecurity,
        userDetailsService: UserDetailsService,
        passwordEncoder: PasswordEncoder): AuthenticationManager {
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder)
            .and()
            .build()
    }

}