package screen.content

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import theme.Theme

@Composable
fun ContentScreen(
    query: String,
    theme: Theme,
    changeTheme: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(theme.background)
    ) {
        Header(query, theme, changeTheme)

        Conversation(modifier = Modifier.weight(1.0f), theme)

        BottomBar(theme)
    }
}
