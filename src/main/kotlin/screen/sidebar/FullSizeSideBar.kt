package screen.sidebar

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DoubleArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import image_loader.ImageLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import theme.Theme

@Composable
@Preview
fun FullSizeSideBar(
    collapseIconOnClick: () -> Unit,
    theme: Theme
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
                theme = theme
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

@Composable
fun SearchBar(
    onValueChange: (String) -> Unit,
    collapseIconOnClick: () -> Unit,
    theme: Theme
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(79.dp)
            .border(BorderStroke(1.dp, color = theme.border))
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            var text by remember { mutableStateOf("") }

            CompositionLocalProvider(
                LocalTextSelectionColors provides TextSelectionColors(
                    handleColor = theme.blue,
                    backgroundColor = theme.blue
                )
            ) {
                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                        onValueChange(it)
                    },
                    textStyle = TextStyle(

                    ),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .border(
                            border = BorderStroke(0.dp, Color.Transparent),
                            shape = CutCornerShape(0)
                        ),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = theme.body,
                        cursorColor = theme.cursor,
                        placeholderColor = theme.body,
                        backgroundColor = theme.background,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(0),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = theme.body
                        )
                    },
                    placeholder = {
                        Text("Search")
                    }
                )
            }

            Icon(
                imageVector = Icons.Default.DoubleArrow,
                tint = theme.blue,
                contentDescription = null,
                modifier = Modifier
                    .rotate(180.0f)
                    .size(40.dp)
                    .clickable { collapseIconOnClick() }
            )
        }
    }
}

@Composable
fun ContactScreen(
    query: String,
    theme: Theme
) {
    val list = listOf("Pityu", "Jozsi", "Bela", "Istvan", "Bence", "Cecilia")
    val scrollState = rememberLazyListState()

    Box {
        LazyColumn(state = scrollState) {
            items(list.size) { index ->
                val name = list[index]
                if (name.lowercase().contains(query.lowercase())) {
                    Contact(name, theme = theme)
                }
            }
        }

        VerticalScrollbar(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(
                scrollState = scrollState
            )
        )
    }

}

@Composable
fun Contact(
    name: String,
    theme: Theme
) {
    val message = "Asdasdasdasdasdasdasdsadasdsadasdasddasdasdasdasdasdasdasdasdsadasdsadasd"
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(87.dp)
            .padding(top = 8.dp)
            .border(BorderStroke(1.dp, color = theme.border))
    ) {
        Row {
            Box(modifier = Modifier.padding(8.dp)) {
                NetworkImage(
                    url = "https://lh3.googleusercontent.com/2hDpuTi-0AMKvoZJGd-yKWvK4tKdQr_kLIpB_qSeMau2TNGCNidAosMEvrEXFO9G6tmlFlPQplpwiqirgrIPWnCKMvElaYgI-HiVvXc=w600",
                    modifier = Modifier.clip(CircleShape)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp)
                    .weight(1.0f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.title
                    )
                    Text(
                        text = "13:59",
                        fontSize = 12.sp,
                        color = theme.body
                    )
                }
                Box(modifier = Modifier.width(199.dp).padding(top = 8.dp)) {
                    Text(
                        maxLines = 2,
                        text = "${message.take(60)}...",
                        fontSize = 14.sp,
                        color = theme.body
                    )
                }
            }
        }
    }
}

@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier = Modifier,
) {
    val bitmap: ImageBitmap? by produceState<ImageBitmap?>(null) {
        value = withContext(Dispatchers.IO) {
            ImageLoader.load(url)
        }
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap!!,
            contentDescription = null,
            modifier = modifier,
        )
    } else {
        Box(modifier = Modifier.height(79.dp).width(79.dp))
    }

}

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
                imageVector = Icons.Default.Add, contentDescription = null, tint = Color.Black
            )
            Text(
                text = text, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black
            )
        }
    }
}

