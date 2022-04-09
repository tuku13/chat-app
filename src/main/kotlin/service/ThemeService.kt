package service

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import theme.DarkTheme
import theme.LightTheme
import theme.Theme

class ThemeService {
    private var _theme: MutableStateFlow<Theme> = MutableStateFlow(LightTheme)
    val theme: StateFlow<Theme>
        get() = _theme

    fun changeTheme() {
        when (_theme.value) {
            is LightTheme -> _theme.tryEmit(DarkTheme)
            is DarkTheme -> _theme.tryEmit(LightTheme)
        }
    }
}