package image_loader

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import org.jetbrains.skia.Image
import java.util.*

object ImageLoader {
    private val images: MutableMap<String, ImageBitmap> = mutableMapOf()
    private val client = HttpClient(CIO)

    suspend fun load(url: String): ImageBitmap {
        images[url]?.let { return it }

        val image: ByteArray = client.get(url).body()
        val bitmap = Image.makeFromEncoded(image).toComposeImageBitmap()
        images[url] = bitmap

        return bitmap
    }

//    fun load(uuid: UUID): ImageBitmap? {
//        images[uuid.toString()]?.let { return it }
//        return null
//    }

//    fun store(bytes: ByteArray) : UUID {
//        val bitmap = Image.makeFromEncoded(bytes).toComposeImageBitmap()
//        val uuid = UUID.randomUUID()
//        images[uuid.toString()] = bitmap
//
//        return uuid
//    }

}