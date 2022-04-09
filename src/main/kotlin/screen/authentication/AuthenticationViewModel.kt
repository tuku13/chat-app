package screen.authentication

import service.AuthenticationService
import util.NetworkResult

class AuthenticationViewModel(
    private val authenticationService: AuthenticationService
) {
    suspend fun login(email: String, password: String): NetworkResult<Boolean> {
        return authenticationService.login(email, password)
    }

    suspend fun register(username: String, email: String, password: String): NetworkResult<Boolean> {
        return authenticationService.register(username, email, password)
    }

}