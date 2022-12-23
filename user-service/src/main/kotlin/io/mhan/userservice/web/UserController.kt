package io.mhan.userservice.web

import io.mhan.userservice.domain.service.UserService
import io.mhan.userservice.model.AuthToken
import io.mhan.userservice.model.MeResponse
import io.mhan.userservice.model.SignInRequest
import io.mhan.userservice.model.SignUpRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/signup")
    suspend fun signUp(@RequestBody request: SignUpRequest) {
        userService.signUp(request)
    }

    @PostMapping("/signin")
    suspend fun signIn(@RequestBody signInRequest: SignInRequest) =
        userService.signIn(signInRequest)

    @DeleteMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun logout(@AuthToken token: String) {
        userService.logout(token)
    }

    @GetMapping("/me")
    suspend fun get(
        @AuthToken token: String
    ) : MeResponse {
        return MeResponse(userService.getByToken(token))
    }
}