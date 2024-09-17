package dependency

import net.minestom.dependencies.maven.MavenRepository
import net.minestom.dependencies.maven.MavenResolver
import org.junit.jupiter.api.Test
import java.io.File

object test {
    @Test
    fun dependency() {
        val targerFolder = File("libs")
        val repositories = listOf(
            MavenRepository.Jitpack,
            MavenRepository.Central,
            MavenRepository.JCenter,
            MavenRepository("Minecraft Libs", "https://libraries.minecraft.net"),
            MavenRepository("Sponge", "https://repo.spongepowered.org/maven"),
        )
        val resolver = MavenResolver(repositories)
        val resolved = resolver.resolve("com.zaxxer:HikariCP:4.0.3", targerFolder)
        resolved.printTree()
    }
}