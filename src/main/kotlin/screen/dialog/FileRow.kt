package screen.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.Theme
import java.io.File

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FileRow(
    file: File,
    selectedFile: File?,
    theme: Theme,
    onDirectoryDoubleClick: (File) -> Unit,
    onFileClick: (File) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        when {
            file.isDirectory -> {
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
                        imageVector = Icons.Default.Folder,
                        contentDescription = "Folder",
                        tint = theme.body
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = file.name,
                        color = theme.body,
                        fontSize = 14.sp
                    )
                }
            }

            file.isFile -> {
                val backgroundColor = if (file == selectedFile) Color.Black.copy(alpha = 0.2f) else Color.Transparent
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { onFileClick(file) }
                        .fillMaxWidth()
                        .height(32.dp)
                        .background(backgroundColor)
                ) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = "Image",
                        tint = theme.body
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = file.name,
                        color = theme.body,
                        fontSize = 14.sp
                    )
                }
            }
        }

    }
}