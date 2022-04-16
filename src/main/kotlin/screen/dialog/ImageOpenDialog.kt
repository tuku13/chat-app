package screen.dialog

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import screen.authentication.InputTextField
import screen.sidebar.ColoredButton
import service.ThemeService
import java.io.File

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageOpenDialog(
    onCloseRequest: (File?) -> Unit
) {
    Dialog(
        onCloseRequest = { onCloseRequest(null) },
        state = rememberDialogState(
            position = WindowPosition(Alignment.Center),
            size = DpSize(720.dp, 540.dp)
        ),
        resizable = false,
        title = "Choose Image"
    ) {
        val di = localDI()
        val themeService: ThemeService by di.instance()
        val theme = themeService.theme.collectAsState()

        var file by remember { mutableStateOf(File(".")) }
        var path by remember { mutableStateOf(file.absolutePath) }
        var selectedFile: File? by remember { mutableStateOf(null) }
        var showDrives by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize().background(theme.value.background).padding(16.dp)
        ) {
            InputTextField(
                value = path,
                onValueChange = { },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(1.0f)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1.0f)
            ) {
                val children = file.listFiles().filter {
                    it.isDirectory ||
                            it.name.endsWith(".jpg", ignoreCase = true) ||
                            it.name.endsWith(".jpg", ignoreCase = true) ||
                            it.name.endsWith(".webp", ignoreCase = true) ||
                            it.name.endsWith(".gif", ignoreCase = true)
                }

                if(showDrives) {
                    val drives = File.listRoots()
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    items(drives) { drive ->
                        DriveRow(
                            file = drive,
                            theme = theme.value,
                            onDirectoryDoubleClick = {
                                showDrives = false
                                path = it.absolutePath
                                file = it
                            },
                        )
                    }
                } else {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .combinedClickable(
                                    onClick = { },
                                    onDoubleClick = {
                                        try {
                                            val parentFile = file.absoluteFile.parentFile
                                            if (parentFile == null) {
                                                showDrives = true
                                            } else {
                                                file = parentFile
                                                path = file.absolutePath
                                            }
                                        } catch (e: Exception) {
                                            println("${file.absoluteFile}, exception: ${e.message}")
                                        }
                                    }
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Folder,
                                contentDescription = "Parent Folder",
                                tint = theme.value.body
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "...",
                                color = theme.value.body,
                                fontSize = 14.sp
                            )
                        }
                    }
                    items(children) { child ->
                        FileRow(
                            file = child,
                            theme = theme.value,
                            selectedFile = selectedFile,
                            onDirectoryDoubleClick = {
                                path = it.absolutePath
                                file = it
                            },
                            onFileClick = { selectedFile = it }
                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                ColoredButton(
                    text = "Open",
                    onClick = { onCloseRequest(selectedFile) },
                    color = theme.value.blue,
                    theme = theme.value,
                    icon = null
                )
            }
        }
    }
}