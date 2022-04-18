package util

import java.io.File
import java.util.*

object Config {
    var host: String = "localhost"
        private set

    var port: Int = 9090
        private set

    val baseUrl: String
        get() = "$host:$port"

    fun loadConfig() {
        try {
            val configFile = File("./src/main/resources/server.conf")
            val properties = Properties()

            properties.load(configFile.inputStream())
            host = properties.getProperty("server_host")
            port = properties.getProperty("server_port").toInt()
        } catch (e: Exception) {
            println("Config file not found 'src/main/resources/server.conf'")
            println(e)
        }
    }
}
