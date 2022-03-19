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
import io.ktor.client.request.forms.*
import io.ktor.client.response.*
import io.ktor.client.statement.*
import io.ktor.http.*
import screen.main.MainScreen
import theme.DarkTheme
import theme.LightTheme
import theme.Theme

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
        MainScreen(
            theme = theme,
            changeTheme = {
                theme = when (theme) {
                    DarkTheme -> LightTheme
                    LightTheme -> DarkTheme
                }
            }
        )
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


