package screen.sidebar

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import model.Room
import theme.Theme

@Composable
@Preview
fun FullSizeSideBar(
    collapseIconOnClick: () -> Unit,
    theme: Theme,
    rooms: List<Room>
) {
    Column(
        modifier = Modifier.width(328.dp)
            .fillMaxHeight()
            .background(theme.background)
            .border(BorderStroke(1.dp, color = theme.border))
    ) {
        var query by remember { mutableStateOf("") }

        SearchBar(
            onValueChange = { query = it },
            collapseIconOnClick = collapseIconOnClick,
            theme = theme
        )

        Box(modifier = Modifier.weight(1.0f)) {
            ContactScreen(
                query = query,
                theme = theme,
                rooms = rooms
            )
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, color = theme.border)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(modifier = Modifier.height(16.dp))

                ColoredButton(
                    text = "Create or Join Group",
                    color = theme.green,
                    theme = theme
                )

                Box(modifier = Modifier.height(16.dp))

                ColoredButton(
                    text = "New Contact",
                    color = theme.blue,
                    theme = theme
                )

                Box(modifier = Modifier.height(16.dp))
            }
        }
    }
}
