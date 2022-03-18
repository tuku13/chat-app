package image_loader

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import org.jetbrains.skia.Image

object ImageLoader {
    private val images: MutableMap<String, ImageBitmap> = mutableMapOf()
    private val client = HttpClient(OkHttp)

    suspend fun load(url: String): ImageBitmap {
        images[url]?.let { return it }

        val image = client.get<ByteArray>(url)
        val bitmap = Image.makeFromEncoded(image).toComposeImageBitmap()
        images[url] = bitmap

        return bitmap
    }
}