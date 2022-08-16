package study.spring.security.jwt_security.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class TokenProvider {

    companion object {
        private const val JWT_SECRET: String = "secret"
        private val JWT_ALGORITHM: Algorithm = Algorithm.HMAC256(JWT_SECRET.encodeToByteArray())
    }

    fun issueToken(
        sub: String,
        iss: String,
        roles: List<String>
    ): TokenInfo {
        val baseTime = Instant.now()
        val accessToken = issueAccessToken(sub, iss, roles, baseTime)
        val refreshToken = issueRefreshToken(sub, iss, baseTime)

        return TokenInfo(accessToken, refreshToken)
    }

    private fun issueAccessToken(
        sub: String,
        iss: String,
        roles: List<String>,
        baseTime: Instant
    ) = JWT.create()
        .withSubject(sub)
        .withIssuer(iss)
        .withExpiresAt(baseTime.plusMillis(10 * 60 * 1000))
        .withJWTId(UUID.randomUUID().toString())
        .withClaim("roles", roles)
        .sign(JWT_ALGORITHM)

    private fun issueRefreshToken(
        sub: String,
        iss: String,
        baseTime: Instant
    ) = JWT.create()
        .withSubject(sub)
        .withIssuer(iss)
        .withExpiresAt(baseTime.plusMillis(30 * 60 * 1000))
        .withJWTId(UUID.randomUUID().toString())
        .sign(JWT_ALGORITHM)
}

data class TokenInfo(
    val accessToken: String,
    val refreshToken: String,
)