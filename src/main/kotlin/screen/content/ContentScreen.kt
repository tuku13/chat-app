package screen.content

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
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

        if(selectedRoom != null) {
            Conversation(modifier = Modifier.weight(1.0f), selectedRoom)
            BottomBar()
        } else {
            Box(
                modifier = Modifier.fillMaxSize().background(theme.background),
            ) {
                Text(
                    text = "No room selected.",
                    fontSize = 20.sp,
                    color = theme.body,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

    }
}
