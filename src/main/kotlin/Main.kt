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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import model.Message
import screen.authentication.AuthenticationScreen
import theme.LightTheme
import theme.Theme
import kotlin.random.Random

@Composable
@Preview
fun App() {
    var theme: Theme by remember { mutableStateOf(LightTheme) }

    MaterialTheme {
//        Button(onClick = {
//            text = "Hello, Desktop!"
//        }) {
//            Text(text)
//        }

        AuthenticationScreen(theme)

//        MainScreen(
//            theme = theme,
//            changeTheme = {
//                theme = when (theme) {
//                    DarkTheme -> LightTheme
//                    LightTheme -> DarkTheme
//                }
//            }
//        )
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


