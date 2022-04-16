package image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toComposeImageBitmap

@Composable
fun LocalImage(
    byteArray: ByteArray,
    modifier: Modifier = Modifier,
) {

    if(byteArray.isEmpty()) {
        return
    }

    val bitmap = org.jetbrains.skia.Image.makeFromEncoded(byteArray).toComposeImageBitmap()

    Image(
        bitmap = bitmap,
        contentDescription = null,
        modifier = modifier
    )
}

