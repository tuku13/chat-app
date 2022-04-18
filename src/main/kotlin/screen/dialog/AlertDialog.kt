package screen.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState

@Composable
fun AlertDialog(
    onCloseRequest: () -> Unit,
    text: String
) {
    Dialog(
        title = "Information",
        onCloseRequest = onCloseRequest,
        state = rememberDialogState(position = WindowPosition(Alignment.Center))
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}