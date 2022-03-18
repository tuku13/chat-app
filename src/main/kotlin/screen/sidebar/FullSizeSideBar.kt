package screen.sidebar

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DoubleArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import image_loader.ImageLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        Row(
            modifier = Modifier.fillMaxHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
            ) {

            var text by remember { mutableStateOf("") }

            TextField(
                value = text,
                onValueChange = { text = it},
                modifier = Modifier.padding(start = 8.dp),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            )

            Icon(
                imageVector = Icons.Default.DoubleArrow,
                tint = Color(0xFF99DCEC),
                contentDescription = null,
                modifier = Modifier
                    .rotate(180.0f)
                    .size(40.dp)
            )
        }
    }
}

@Composable
fun ContactScreen() {
    val list = (1..15).toList()
    val scrollState = rememberLazyListState()

    Box{
        LazyColumn(state = scrollState) {
            items(list) { n ->
                Contact(n)
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
fun Contact(number: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(87.dp)
            .padding(top = 8.dp)
            .border(BorderStroke(1.dp, color = Color(0xFFE5E5E5)))
    ) {
        Row {
            NetworkImage(
                url = "https://lh3.googleusercontent.com/2hDpuTi-0AMKvoZJGd-yKWvK4tKdQr_kLIpB_qSeMau2TNGCNidAosMEvrEXFO9G6tmlFlPQplpwiqirgrIPWnCKMvElaYgI-HiVvXc=w600"
            )
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

    if(bitmap != null) {
        Image(
            bitmap = bitmap!!,
            contentDescription = null,
            modifier = modifier
        )
    }

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

