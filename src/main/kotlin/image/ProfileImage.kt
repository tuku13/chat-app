package image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import di.DIContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.skia.Image
import org.kodein.di.instance
import service.ImageService
import java.util.*

@Composable
fun ProfileImage(
    userId: String,
    modifier: Modifier = Modifier
) {
    val bitmap: ImageBitmap? by produceState<ImageBitmap?>(null) {
        value = withContext(Dispatchers.IO) {
            val imageService: ImageService by DIContainer.di.instance()

            val base64EncodedImage = imageService.getImage(userId)
            val bytes = Base64.getDecoder().decode(base64EncodedImage)

            createImage(bytes)
        }
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap!!,
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.FillBounds
        )
    } else {
        Box(modifier = Modifier.size(64.dp))
    }
}

fun createImage(bytes: ByteArray) : ImageBitmap? {
    return try {
        Image.makeFromEncoded(bytes).toComposeImageBitmap()
    } catch (e: Exception) {
        null
    }
}