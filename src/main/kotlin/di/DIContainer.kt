package di

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import repository.MessageRepository
import repository.RoomRepository
import screen.authentication.AuthenticationViewModel
import screen.main.MainViewModel
import service.*

object DIContainer {
    val di = DI {
        bindSingleton<RoomRepository> {
            val client: HttpClient by di.instance()
            val authenticationService: AuthenticationService by di.instance()

            RoomRepository(client, authenticationService)
        }

        bindSingleton<CookiesStorage> { AcceptAllCookiesStorage() }

        bindSingleton<HttpClient> {
            val cookiesStorage: CookiesStorage by di.instance()

            HttpClient(CIO) {
                install(WebSockets)

                install(HttpCookies) {
                    storage = cookiesStorage
                }

                install(ContentNegotiation) {
                    json()
                }

                expectSuccess = true
                HttpResponseValidator {
                    handleResponseException { exception ->
                        val clientException = exception as? ClientRequestException ?: return@handleResponseException
                        val exceptionResponse = exception.response
                        val exceptionResponseText = exceptionResponse.bodyAsText()
                        throw ResponseException(exceptionResponse, exceptionResponseText)
                    }
                }
            }
        }

        bindSingleton {
            val roomRepository: RoomRepository by di.instance()
            MainViewModel(roomRepository)
        }

        bindSingleton {
            val client: HttpClient by di.instance()
            AuthenticationService(client)
        }

        bindSingleton {
            val service: AuthenticationService by di.instance()
            AuthenticationViewModel(service)
        }

        bindSingleton { ThemeService() }

        bindSingleton {
            val client: HttpClient by di.instance()
            UserService(client)
        }

        bindSingleton {
            val client: HttpClient by di.instance()
            MessageRepository(client)
        }

        bindSingleton {
            val client: HttpClient by di.instance()
            WebSocketService(client)
        }

        bindSingleton {
            val client: HttpClient by di.instance()
            ImageService(client)
        }

    }
}