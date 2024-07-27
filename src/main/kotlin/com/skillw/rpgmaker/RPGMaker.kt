package com.skillw.rpgmaker

import com.skillw.rpgmaker.core.handlers.awake.AwakeManager
import com.skillw.rpgmaker.core.handlers.awake.AwakeType
import com.skillw.rpgmaker.event.init.ManagerInit
import com.skillw.rpgmaker.manager.ManagerData
import com.skillw.rpgmaker.utils.handler
import com.skillw.rpgmaker.world.WorldInfo
import net.minestom.server.MinecraftServer
import taboolib.module.configuration.Configuration
import taboolib.module.configuration.Type
import java.io.File

class RPGMaker(private val minecraftServer: MinecraftServer) {

    val managerData = ManagerData("Server")

    //manager进入初始化阶段
    private fun managerLoader() {
        RPGMakerInstance.allManagers.forEach { (_, value) ->
            value.onLoad()
        }
        ManagerInit()
    }

    /*
    * 初始化Awake
    *      Manager
    *      Registry
    *      ClassQuery
    * */
    private fun handlerInit() {
        handler(
            "com.skillw.rpgmaker",
            filterPack = setOf(
                "com.skillw.rpgmaker.server",
                "com.skillw.rpgmaker.utils"
            )
        )
    }
    fun init() {

        handlerInit()
        managerLoader()

        RPGMakerInstance.allManagers.forEach { (_, value) -> value.onEnable()}
        AwakeManager.execAll(AwakeType.Enable)
        MinecraftServer.getSchedulerManager().buildShutdownTask { AwakeManager.execAll(AwakeType.Disable) }
        minecraftServer.start("127.0.0.1", 25565)
        AwakeManager.execAll(AwakeType.Active)
    }
}