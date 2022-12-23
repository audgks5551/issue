package io.mhan.userservice.domain.service

import io.mhan.userservice.config.JWTProperties
import io.mhan.userservice.domain.entity.User
import io.mhan.userservice.domain.respository.UserRepository
import io.mhan.userservice.exception.PasswordNotMatchedException
import io.mhan.userservice.exception.UserExistsException
import io.mhan.userservice.exception.UserNotFoundException
import io.mhan.userservice.model.SignInRequest
import io.mhan.userservice.model.SignInResponse
import io.mhan.userservice.model.SignUpRequest
import io.mhan.userservice.utils.BCryptUtils
import io.mhan.userservice.utils.JWTClaim
import io.mhan.userservice.utils.JWTUtils
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtProperties: JWTProperties,
    private val cacheManager: CoroutineCacheManager<User>,
) {

    companion object {
        private val CACHE_TTL = Duration.ofMinutes(1)
    }

    suspend fun signUp(signUpRequest: SignUpRequest) {
        with(signUpRequest) {
            userRepository.findByEmail(email)?.let {
                throw UserExistsException()
            }

            val user = User(
                email = email,
                password = BCryptUtils.hash(password),
                username = username,
            )

            userRepository.save(user)
        }


    }

    suspend fun signIn(signInRequest: SignInRequest): SignInResponse {
        return with(userRepository.findByEmail(signInRequest.email) ?: throw UserNotFoundException()) {
            val verified = BCryptUtils.verify(signInRequest.password, password)

            if (!verified) {
                throw PasswordNotMatchedException()
            }

            val jwtClaim = JWTClaim(
                userId = id!!,
                email = email,
                profileUrl = profileUrl,
                username = username
            )

            val token = JWTUtils.createToken(jwtClaim, jwtProperties)
            cacheManager.awaitPut(key = token, value = this, ttl = CACHE_TTL)

            SignInResponse(
                email = email,
                username = username,
                token = token
            )
        }
    }

    suspend fun logout(token: String) {
        cacheManager.awaitEvict(token)
    }
}