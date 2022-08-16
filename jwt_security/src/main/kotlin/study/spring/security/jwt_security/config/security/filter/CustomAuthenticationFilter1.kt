package study.spring.security.jwt_security.config.security.filter

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthenticationFilter(
    private val authManager: AuthenticationManager
): UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?
    ): Authentication {
        val username = request?.getParameter("username") ?: ""
        val password = request?.getParameter("password") ?: ""
        val authenticationToken = UsernamePasswordAuthenticationToken(username, password)

        return authManager.authenticate(authenticationToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        super.successfulAuthentication(request, response, chain, authResult)
    }
}