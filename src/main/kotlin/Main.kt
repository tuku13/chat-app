// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import model.Message
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.compose.localDI
import org.kodein.di.compose.withDI
import org.kodein.di.instance
import repository.RoomRepository
import screen.authentication.AuthenticationScreen
import screen.authentication.AuthenticationViewModel
import screen.main.MainScreen
import screen.main.MainViewModel
import service.AuthenticationService
import service.ThemeService
import theme.DarkTheme
import theme.LightTheme
import theme.Theme
import kotlin.random.Random
const val BASE_URL: String = "http://0.0.0.0:9090"

val di = DI {
    bindSingleton<RoomRepository> {
        val client: HttpClient by di.instance()
        RoomRepository(client)
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

}

@Composable
@Preview
fun App() = withDI(di) {
    val authenticationService: AuthenticationService by di.instance()
    val themeService: ThemeService by localDI().instance()

    val loggedIn = authenticationService.authenticated.collectAsState()
    val theme = themeService.theme.collectAsState().value

    MaterialTheme {
        if(loggedIn.value) {
            MainScreen(
                theme = theme,
                changeTheme = { themeService.changeTheme() }
            )
        } else {
            AuthenticationScreen()
        }

    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Chat App",
        state = WindowState(size = DpSize(1024.dp, 768.dp))
    ) {
        App()
    }
}

fun pollMessages(): Flow<Message> = flow {
    while (true) {
        delay(3 * 1000L)
    }
}


