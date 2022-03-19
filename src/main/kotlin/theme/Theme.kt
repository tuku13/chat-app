package theme

import androidx.compose.ui.graphics.Color

sealed class Theme(
    val green: Color,
    val blue: Color,
    val background: Color,
    val textFieldBackground: Color,
    val title: Color,
    val body: Color,
    val chatBackground: Color,
    val chatText: Color,
    val border: Color
)

object LightTheme : Theme(
    green = Color(0xFF99ECAF),
    blue = Color(0xFF99DCEC),
    background = Color(0xFFFFFFFF),
    textFieldBackground = Color(0xFFE5E5E5),
    title = Color(0xFF000000),
    body = Color(0xFF858585),
    chatBackground = Color(0xFFC4C4C4),
    chatText = Color(0xFF000000),
    border = Color(0xFFE5E5E5)
)

object DarkTheme : Theme(
    green = Color(0xFF99ECAF),
    blue = Color(0xFF99DCEC),
    background = Color(0xFF121212),
    textFieldBackground = Color(0xFF272727),
    title = Color(0xFFC4C4C4),
    body = Color(0xFF888888),
    chatBackground = Color(0xFF272727),
    chatText = Color(0xFF888888),
    border = Color(0xFF272727)
)
