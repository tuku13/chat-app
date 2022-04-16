package screen.content

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import model.Room
import theme.Theme

@Composable
fun ContentScreen(
    selectedRoom: Room?,
    theme: Theme,
    changeTheme: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(theme.background)
    ) {
        Header(changeTheme)

        Conversation(modifier = Modifier.weight(1.0f), selectedRoom)

        BottomBar()
    }
}
