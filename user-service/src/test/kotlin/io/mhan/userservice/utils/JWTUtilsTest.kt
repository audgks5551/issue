package io.mhan.userservice.utils

import com.auth0.jwt.interfaces.DecodedJWT
import io.mhan.userservice.config.JWTProperties
import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class JWTUtilsTest {
    private val logger = KotlinLogging.logger{}

    @Test
    fun createTokenTest() {
        val jwtClaim = JWTClaim(
            userId = 1,
            email = "dev@gmail.com",
            profileUrl = "profile.jpg",
            username = "개발자"
        )

        val properties = JWTProperties(
            issuer = "jara",
            subject = "auth",
            expiresTime = 3600,
            secret = "my-secret"
        )

        val token = JWTUtils.createToken(jwtClaim, properties)

        assertThat(token).isNotNull

        logger.info { "token : $token" }
    }

    @Test
    fun decodeTest() {
        val jwtClaim = JWTClaim(
            userId = 1,
            email = "dev@gmail.com",
            profileUrl = "profile.jpg",
            username = "개발자"
        )

        val properties = JWTProperties(
            issuer = "jara",
            subject = "auth",
            expiresTime = 3600,
            secret = "my-secret"
        )

        val token = JWTUtils.createToken(jwtClaim, properties)

        val decode: DecodedJWT = JWTUtils.decode(token, secret = properties.secret, issuer = properties.issuer)

        with(decode) {
            logger.info { "claim: $claims" }

            val userId = claims["userId"]!!.asLong()
            assertThat(userId).isEqualTo(jwtClaim.userId)

            val email = claims["email"]!!.asString()
            assertThat(email).isEqualTo(jwtClaim.email)

            val profileUrl = claims["profileUrl"]!!.asString()
            assertThat(profileUrl).isEqualTo(jwtClaim.profileUrl)

            val username = claims["username"]!!.asString()
            assertThat(username).isEqualTo(jwtClaim.username)
        }
    }
}