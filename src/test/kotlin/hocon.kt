import org.junit.jupiter.api.Test
import taboolib.module.configuration.Configuration
import taboolib.module.configuration.Type
import java.io.File

object Hocon {
    @Test
    fun testVar() {
        val hocon = Configuration.loadFromFile(File("server.conf"), Type.HOCON)
        println(hocon.getString("server.debug"))
    }
}