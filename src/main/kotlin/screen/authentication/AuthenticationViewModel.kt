package screen.authentication

import service.AuthenticationService
import util.NetworkResult

class AuthenticationViewModel(
    private val authenticationService: AuthenticationService
) {
    suspend fun login(email: String, password: String): Boolean {
        return when (authenticationService.login(email, password)) {
            is NetworkResult.Success -> true
            is NetworkResult.Error -> false
        }
    }

    suspend fun register(username: String, email: String, password: String): Boolean {
        return when (authenticationService.register(username, email, password)){
            is NetworkResult.Success -> true
            is NetworkResult.Error -> false
        }
    }

}