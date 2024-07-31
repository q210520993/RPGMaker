package com.skillw.rpgmaker.manager.sub

import com.skillw.rpgmaker.RPGMakerInstance.reg
import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import com.skillw.rpgmaker.core.map.KeyMap
import com.skillw.rpgmaker.launcher.LoggerColor
import com.skillw.rpgmaker.utils.ResourceUtil
import com.skillw.rpgmaker.world.SimpleWorld
import com.skillw.rpgmaker.world.WorldInfo
import com.skillw.rpgmaker.world.WorldNotFoundException
import net.minestom.server.MinecraftServer
import net.minestom.server.instance.anvil.AnvilLoader
import org.slf4j.LoggerFactory
import taboolib.module.configuration.Configuration
import java.io.File
import java.io.FileNotFoundException


@AutoRegistry
object WorldManagerImpl: WorldManager, KeyMap<String, SimpleWorld>() {

    val logger = LoggerFactory.getLogger(this.javaClass)!!

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

    override fun onEnable() {
        logger.info("${LoggerColor.GREEN}WorldManager is enabled!${LoggerColor.RESET}")
    }

    /**
     * 从指定文件加载世界。
     *
     * @param file 表示世界目录的文件。
     * @throws FileNotFoundException 如果指定的文件不存在。
     */
    override fun loadWorld(file: File) {
        var worldInfo: WorldInfo? = null
        // 检查文件是否存在
        file.listFiles() ?: throw FileNotFoundException("File ${file.name} does not exist")
        // 检查是否存在 config.yml 文件
        val has = file.listFiles()?.filter { it.name == "config.yml" }?.size != 0
        if (!has) throw WorldNotFoundException("World Config ${file.name} does not exist")
        // 遍历文件列表
        file.listFiles()?.forEach { theFile ->
            if(theFile.name == "config.yml") {
                val config = Configuration.loadFromFile(theFile)
                // 反序列化配置文件为 WorldInfo 对象
                worldInfo = Configuration.deserialize<WorldInfo>(config, ignoreConstructor = true)
            }
        }
        // 检查 WorldInfo 是否为空
        worldInfo ?: throw WorldNotFoundException("World Info is Exception")
        // 创建实例容器
        val instance = MinecraftServer.getInstanceManager().createInstanceContainer()
        // 设置区块加载器
        instance.chunkLoader = AnvilLoader(file.path)

        // 注册世界
        SimpleWorld(instance, worldInfo!!).register()
    }

    override fun onDisable() {
        WorldManagerImpl.filter {
            !it.value.worldInfo.unSave
        }.forEach {

            it.value.instance.saveInstance()
        }
    }

}