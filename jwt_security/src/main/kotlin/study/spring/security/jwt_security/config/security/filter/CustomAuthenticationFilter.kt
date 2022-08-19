package study.spring.security.jwt_security.config.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import study.spring.security.jwt_security.config.security.TokenProvider
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthenticationFilter(
    private val authManager: AuthenticationManager,
): UsernamePasswordAuthenticationFilter() {

    // 연동을 시도할 때, 사용자 패스워드와 비밀번호로 인증을 시도한다
    override fun attemptAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?
    ): Authentication {
        val username = request?.getParameter("username") ?: ""
        val password = request?.getParameter("password") ?: ""
        val authenticationToken = UsernamePasswordAuthenticationToken(username, password)

        return authManager.authenticate(authenticationToken)
    }

    // 인증이 성공했을 때, 토큰 발급
    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        val user: User = authResult?.principal as User
        val tokenInfo = TokenProvider.issueToken(
            sub = user.username,
            iss = request?.requestURL.toString(),
            user.authorities.map { it.authority }.toList()
        )

        val tokens: Map<String, String> = mapOf(
            "access_token" to tokenInfo.accessToken,
            "refresh_token" to tokenInfo.refreshToken)

        response?.let {
            it.contentType = MediaType.APPLICATION_JSON_VALUE
            ObjectMapper().writeValue(it.outputStream, tokens)
        }
    }
}