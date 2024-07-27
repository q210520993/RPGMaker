package com.skillw.rpgmaker.manager.sub

import com.skillw.rpgmaker.RPGMakerInstance.reg
import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import com.skillw.rpgmaker.core.map.BaseMap
import com.skillw.rpgmaker.core.map.KeyMap
import com.skillw.rpgmaker.utils.ResourceUtil
import com.skillw.rpgmaker.world.SimpleWorld
import com.skillw.rpgmaker.world.WorldInfo
import com.skillw.rpgmaker.world.WorldNotFoundException
import net.minestom.server.MinecraftServer
import net.minestom.server.instance.anvil.AnvilLoader
import taboolib.module.configuration.Configuration
import taboolib.module.configuration.Type
import java.io.File
import java.io.FileNotFoundException

@AutoRegistry
object WorldManagerImpl: WorldManager, KeyMap<WorldInfo, SimpleWorld>() {

    val Worlds = BaseMap<String, SimpleWorld>()

    private fun readResolve(): Any = WorldManagerImpl
    override val priority = -1

    override val key: String = "WorldManager"

    override fun register() {
        reg()
    }

    override fun onLoad() {
        ResourceUtil.extractResource("worlds")
        val file = File("./worlds")
        file.listFiles()?.forEach { worldFile ->
            loadWorld(worldFile)
        }
    }

    override fun loadWorld(file: File) {
        var worldInfo: WorldInfo? = null
        file.listFiles() ?: throw FileNotFoundException("File ${file.name} does not exist")
        val has = file.listFiles()?.filter { it.name == "config.yml" }?.size != 0
        if (!has) throw WorldNotFoundException("World Config ${file.name} does not exist")
        file.listFiles()?.forEach { theFile ->
            if(theFile.name == "config.yml") {
                val config = Configuration.loadFromFile(theFile)
                worldInfo = Configuration.deserialize<WorldInfo>(config, ignoreConstructor = true)
            }
        }
        worldInfo ?: throw WorldNotFoundException("World Info is Exception")
        val instance = MinecraftServer.getInstanceManager().createInstanceContainer()
        instance.chunkLoader = AnvilLoader(file.path)
        SimpleWorld(instance, worldInfo!!).register()
    }

}