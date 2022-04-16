package screen.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Storage
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.Theme
import java.io.File

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DriveRow(
    file: File,
    theme: Theme,
    onDirectoryDoubleClick: (File) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .combinedClickable(
                    onClick = { },
                    onDoubleClick = { onDirectoryDoubleClick(file) }
                )
                .height(32.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Storage,
                contentDescription = "Drive",
                tint = theme.body
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = file.toString(),
                color = theme.body,
                fontSize = 14.sp
            )
        }

    }
}