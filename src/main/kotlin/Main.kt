// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import model.Message
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.compose.withDI
import org.kodein.di.instance
import repository.RoomRepository
import screen.authentication.AuthenticationScreen
import screen.main.MainScreen
import screen.main.MainViewModel
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
        }
    }

    bindSingleton {
        val roomRepository: RoomRepository by di.instance()
        MainViewModel(roomRepository)
    }

}

@Composable
@Preview
fun App() = withDI(di) {
    var theme: Theme by remember { mutableStateOf(LightTheme) }
    var loggedIn by remember { mutableStateOf(false) }

    val client: HttpClient by di.instance()

    MaterialTheme {
        if(loggedIn) {
            MainScreen(
                theme = theme,
                changeTheme = {
                    theme = when (theme) {
                        DarkTheme -> LightTheme
                        LightTheme -> DarkTheme
                    }
                },
                logout = { loggedIn = false }
            )
        } else {
            AuthenticationScreen(
                theme = theme,
                client = client,
                onLogin = { loggedIn = true }
            )
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
        emit(
            Message(
                username = "user" + Random.nextInt(1000),
                text = "x".repeat(Random.nextInt(3, 350)),
                own = Random.nextBoolean()
            )
        )
        delay(3 * 1000L)
    }
}


