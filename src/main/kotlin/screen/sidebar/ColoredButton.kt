package screen.sidebar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import theme.Theme

@Composable
fun ColoredButton(
    text: String,
    color: Color,
    theme: Theme,
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
                imageVector = Icons.Default.Add, contentDescription = null, tint = theme.cursor
            )
            Text(
                text = text, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = theme.cursor
            )
        }
    }
}