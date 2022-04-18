// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import di.DIContainer
import org.kodein.di.compose.withDI
import org.kodein.di.instance
import screen.authentication.AuthenticationScreen
import screen.main.MainScreen
import service.AuthenticationService
import service.ThemeService
import util.Config

@Composable
@Preview
fun App() = withDI(DIContainer.di) {

    val authenticationService: AuthenticationService by DIContainer.di.instance()
    val themeService: ThemeService by DIContainer.di.instance()

    val loggedIn = authenticationService.authenticated.collectAsState()
    val theme = themeService.theme.collectAsState()

    MaterialTheme {
        if (loggedIn.value) {
            MainScreen(
                theme = theme.value,
                changeTheme = { themeService.changeTheme() }
            )
        } else {
            AuthenticationScreen()
        }

    }
}

fun main() = application {
    Config.loadConfig()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Chat App",
        state = WindowState(size = DpSize(1024.dp, 768.dp)),
//        resizable = false
    ) {
        App()
    }
}



