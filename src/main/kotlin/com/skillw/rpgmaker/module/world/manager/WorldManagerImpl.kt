package com.skillw.rpgmaker.module.world.manager

import com.skillw.rpgmaker.RPGMakerInstance.reg
import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import com.skillw.rpgmaker.core.map.BaseMap
import com.skillw.rpgmaker.core.map.KeyMap
import com.skillw.rpgmaker.module.system.terminal.LoggerColor
import com.skillw.rpgmaker.utils.ResourceUtil
import com.skillw.rpgmaker.module.world.SimpleWorld
import com.skillw.rpgmaker.module.world.WorldInfo
import com.skillw.rpgmaker.module.world.WorldNotFoundException
import com.skillw.rpgmaker.utils.PosUtil
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.instance.Instance
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.instance.InstanceManager
import net.minestom.server.instance.anvil.AnvilLoader
import org.slf4j.LoggerFactory
import taboolib.module.configuration.Configuration
import taboolib.module.configuration.Type
import java.io.File
import java.io.FileNotFoundException
import java.util.*


@AutoRegistry
object WorldManagerImpl: WorldManager, KeyMap<String, SimpleWorld>() {

    var defaultLoader: WorldLoader = DefaultLoader

    val worldUUIDManager = BaseMap<UUID, SimpleWorld>()

    private val logger = LoggerFactory.getLogger(this.javaClass)!!

    private fun readResolve(): Any = WorldManagerImpl

    override fun getKey(): String {
        return "WorldManager"
    }

    override fun register() {
        reg()
    }

    override fun onLoad() {
        val file = File("worlds")
        file.listFiles()?.forEach { worldFile ->
            loadWorld(worldFile, defaultLoader)
        }
    }

    fun loadWorld(file: File, worldLoader: WorldLoader = defaultLoader): SimpleWorld {
        val world = worldLoader.loadWorld(file)
        world.worldLoader = worldLoader
        this.register(world)
        worldUUIDManager[world.instance.uniqueId] = world
        return world
    }

    override fun onEnable() {
        logger.info("${LoggerColor.GREEN}WorldManager is enabled!${LoggerColor.RESET}")
    }

    override fun onDisable() {
        WorldManagerImpl.filter {
            !it.value.worldInfo.unSave
        }.forEach {
            it.value.instance.saveChunksToStorage()
        }
    }

}