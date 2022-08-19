package study.spring.security.jwt_security.config.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import study.spring.security.jwt_security.config.security.TokenProvider
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthorizationFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 로그인이면 인증을 하지 않는다
        if (request.servletPath.equals("/api/login")) {
            filterChain.doFilter(request, response)
            return
        }

        // 로그인이 아니면 토큰을 검증한다
        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authorizationHeader?.startsWith("bearer ") == true) {
            try {
                val token = authorizationHeader.substring("bearer ".length)
                val decodedToken = TokenProvider.verify(token)

                val username = decodedToken.subject
                val roles = decodedToken.getClaim("roles").asArray(String::class.java)
                val authorities = mutableListOf<SimpleGrantedAuthority>()
                roles.forEach { authorities.add(SimpleGrantedAuthority(it)) }

                val authenticationToken = UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    authorities)
                SecurityContextHolder.getContext().authentication = authenticationToken
                filterChain.doFilter(request, response)
            } catch (e: Exception) {
                response.status = HttpStatus.FORBIDDEN.value()
                response.contentType = MediaType.APPLICATION_JSON_VALUE
                e.message?.let {
                    val error = mutableMapOf<String, String>()
                    error["error_message"] = e.message ?: ""
                    ObjectMapper().writeValue(response.outputStream, error)
                }
            }
        } else {
            filterChain.doFilter(request, response)
        }
    }
}