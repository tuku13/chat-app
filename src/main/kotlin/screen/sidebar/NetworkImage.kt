package screen.sidebar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import image_loader.ImageLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

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
fun LocalImage(
    uuid: UUID,
    modifier: Modifier = Modifier,
) {
    val bitmap = ImageLoader.load(uuid)

    if (bitmap != null) {
        Image(
            bitmap = bitmap,
            contentDescription = null,
            modifier = modifier
        )
    } else {
        Box(
            modifier = Modifier.padding(8.dp).background(Color.Red).padding(8.dp)
        ) {
            Text(
                text = "Image unavailable",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}