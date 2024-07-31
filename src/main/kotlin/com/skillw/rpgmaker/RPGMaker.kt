package com.skillw.rpgmaker

import ch.qos.logback.core.spi.LifeCycle
import com.skillw.rpgmaker.core.handlers.annotations.Awake
import com.skillw.rpgmaker.core.handlers.awake.AwakeManager
import com.skillw.rpgmaker.core.handlers.awake.AwakeType
import com.skillw.rpgmaker.event.init.ManagerInit
import com.skillw.rpgmaker.manager.ManagerData
import com.skillw.rpgmaker.system.ServerProperties
import com.skillw.rpgmaker.utils.ResourceUtil
import com.skillw.rpgmaker.utils.handler
import net.minestom.server.MinecraftServer
import java.io.File

class RPGMaker(private val minecraftServer: MinecraftServer) {

    val managerData = ManagerData("Server")

    //manager进入初始化阶段
    private fun managerLoader() {
        RPGMakerInstance.allManagers.forEach { (_, value) ->
            value.load()
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
                "com.skillw.rpgmaker.utils",
                "com.skillw.rpgmaker.world",
            )
        )
    }
    fun init() {

        //加载基本的注解处理
        handlerInit()
        //加载manager
        managerLoader()
        //释放文件 初始化serverProperties
        ResourceUtil.extractResource("server.properties")
        val serverProperties = ServerProperties(File("./server.properties"))
        RPGMakerInstance.serverProperties = serverProperties


        AwakeManager.execAll(AwakeType.Enable)
        MinecraftServer.getSchedulerManager().buildShutdownTask { AwakeManager.execAll(AwakeType.Disable) }
        minecraftServer.start(serverProperties.get("server-ip"), serverProperties.get("server-port").toInt())
        AwakeManager.execAll(AwakeType.Active)
    }
}

@Awake(AwakeType.Disable, priority = -1F)
fun disableManager() {
    RPGMakerInstance.allManagers.forEach { (_, value) ->
        value.disable()
    }
}