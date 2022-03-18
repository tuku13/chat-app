package screen.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ContentScreen(query: String) {
    Column(modifier = Modifier
        .fillMaxHeight()
    ) {
        Header()
        Conversation(modifier = Modifier.weight(1.0f))
        BottomBar()
    }
}

@Composable
fun Header() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(79.dp)
        .border(BorderStroke(1.dp, color = Color(0xFFE5E5E5)))
    ) {

    }
}

@Composable
fun Conversation(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .border(BorderStroke(1.dp, color = Color(0xFFE5E5E5)))
    ) {
    }
}

@Composable
fun BottomBar() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(79.dp)
        .border(BorderStroke(1.dp, color = Color(0xFFE5E5E5)))
    ) {

    }
}
