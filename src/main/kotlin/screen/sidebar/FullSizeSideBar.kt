package screen.sidebar

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun FullSizeSideBar() {
    Column(
        modifier = Modifier.width(328.dp)
            .fillMaxHeight()
            .border(BorderStroke(1.dp, color = Color(0xFFE5E5E5)))
    ) {
        SearchBar()

        Box(modifier = Modifier.weight(1.0f)) {
            ContactScreen()
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, color = Color(0xFFE5E5E5))),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(modifier = Modifier.height(16.dp))

                ColoredButton(
                    text = "Create or Join Group",
                    color = Color(0xFF99ECAF)
                )

                Box(modifier = Modifier.height(16.dp))

                ColoredButton(
                    text = "New Contact",
                    color = Color(0xFF99DCEC)
                )

                Box(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun SearchBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(79.dp)
            .border(BorderStroke(1.dp, color = Color(0xFFE5E5E5)))
    ) {

    }
}

@Composable
fun ContactScreen() {

}

@Composable
fun ColoredButton(
    text: String,
    color: Color,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        enabled = true,
        shape = RoundedCornerShape(90.0f),
        colors = ButtonDefaults.buttonColors(backgroundColor = color)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Add, contentDescription = null
            )
            Text(
                text = text, fontWeight = FontWeight.Bold, fontSize = 14.sp
            )
        }
    }
}

