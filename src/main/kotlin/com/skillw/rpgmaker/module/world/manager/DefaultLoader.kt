package com.skillw.rpgmaker.module.world.manager

import com.skillw.rpgmaker.module.world.SimpleWorld
import com.skillw.rpgmaker.module.world.WorldInfo
import com.skillw.rpgmaker.module.world.WorldNotFoundException
import com.skillw.rpgmaker.utils.PosUtil
import net.minestom.server.MinecraftServer
import net.minestom.server.instance.anvil.AnvilLoader
import taboolib.module.configuration.Configuration
import taboolib.module.configuration.Type
import java.io.File
import java.io.FileNotFoundException

object DefaultLoader: WorldLoader {
    override fun loadWorld(file: File): SimpleWorld {
        var worldInfo: WorldInfo? = null
        // 检查文件是否存在
        file.listFiles() ?: throw FileNotFoundException("File ${file.name} does not exist")
        // 检查是否存在 config.yml 文件
        val has = file.listFiles()?.filter { it.name == "config.conf" }?.size != 0
        if (!has) throw WorldNotFoundException("World Config ${file.name} does not exist")
        // 遍历文件列表
        file.listFiles()?.forEach { theFile ->
            if(theFile.name == "config.conf") {
                val config = Configuration.loadFromFile(theFile, Type.HOCON)

                val unSave = config.getBoolean("unSave", false)
                val position = PosUtil.fromString(config.getString("spawnPosition","0.0,0.0,0.0")!!)
                val name = config.getString("name") ?: throw WorldNotFoundException("World Config ${file.name} does not name")
                worldInfo = WorldInfo(name, unSave, position)
            }
        }
        // 检查 WorldInfo 是否为空
        worldInfo ?: throw WorldNotFoundException("World Info is Exception")
        // 创建实例容器
        val instance = MinecraftServer.getInstanceManager().createInstanceContainer()
        // 设置区块加载器
        instance.chunkLoader = AnvilLoader(file.path)
        return SimpleWorld(instance, worldInfo!!)
    }
}